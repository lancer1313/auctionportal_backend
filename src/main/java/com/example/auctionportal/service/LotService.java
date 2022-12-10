package com.example.auctionportal.service;

import com.example.auctionportal.dao.LotDao;
import com.example.auctionportal.dao.LotFileDao;
import com.example.auctionportal.dao.UserDao;
import com.example.auctionportal.dto.LotRequest;
import com.example.auctionportal.dto.LotResponse;
import com.example.auctionportal.dto.MessageResponse;
import com.example.auctionportal.exceptions.FileSavingException;
import com.example.auctionportal.models.Lot;
import com.example.auctionportal.models.LotFile;
import com.example.auctionportal.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LotService {

    private final UserDao userDao;
    private final LotDao lotDao;
    private final LotFileDao lotFileDao;

    private final String FOLDER_PATH = "D:\\Works Web\\webstorm\\auction-frontendv0.1\\lot_files\\";

    public LotService(UserDao userDao, LotDao lotDao, LotFileDao lotFileDao) {
        this.userDao = userDao;
        this.lotDao = lotDao;
        this.lotFileDao = lotFileDao;
    }

    public MessageResponse createLot(LotRequest lotRequest) throws FileSavingException {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        MultipartFile file = lotRequest.getFile();
        String fileName = file.getOriginalFilename();
        String filePath = FOLDER_PATH + UUID.randomUUID() + fileName;
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new FileSavingException("Произошла ошибка сохранения файла");
        }
        LotFile lotFile = new LotFile(fileName, file.getContentType(), filePath);
        User user = userDao.findById(userDetails.getId()).get();
        Lot lot = new Lot(lotRequest.getLotTitle(), lotRequest.getLotDescription(),
                lotRequest.getStartingPrice(), lotRequest.getMinimalStep());
        lot.setFile(lotFile);
        lot.setUser(user);
        lotDao.save(lot);
        lotFileDao.save(lotFile);
        return new MessageResponse("Лот создан");
    }

    // TO DO удаляем файл в файловой системе
    public MessageResponse deleteLot(Long id) {
        lotDao.deleteById(id);
        return new MessageResponse("Лот с id=" + id + " был удален");
    }

    public List<LotResponse> getAllLots() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        User user = userDao.findById(userDetails.getId()).get();
        List<Lot> lots = lotDao.findAllByUser(user);
        List<LotResponse> lotsData = new ArrayList<>();
        for (Lot lot : lots) {
            lotsData.add(new LotResponse(lot.getId(), lot.getLotTitle(), lot.getLotDescription(), lot.getStartingPrice(),
                    lot.getMinimalStep(), lot.getFile().getName(), lot.getFile().getType()));
        }
        return lotsData;
    }

    public MessageResponse redactLot(LotRequest lotRequest, Long id) throws FileSavingException {
        MultipartFile file = lotRequest.getFile();
        Lot lot = lotDao.findById(id).get();

        lot.setLotTitle(lotRequest.getLotTitle());
        lot.setLotDescription(lotRequest.getLotDescription());
        lot.setStartingPrice(lotRequest.getStartingPrice());
        lot.setMinimalStep(lotRequest.getMinimalStep());

        String fileName = file.getOriginalFilename();
        String filePath = FOLDER_PATH + UUID.randomUUID() + fileName;

        LotFile lotFile = lotFileDao.findById(lot.getFile().getId()).get();
        lotFile.setName(fileName);
        lotFile.setType(file.getContentType());
        lotFile.setFilePath(filePath);
        try {
            file.transferTo(new File(filePath));
            // TO DO удаляем файл в файловой системе
        } catch (IOException e) {
            throw new FileSavingException("Ошибка сохранения файла");
        }
        lotDao.save(lot);
        lot.setFile(lotFile);
        lotFileDao.save(lotFile);
        return new MessageResponse("Лот был изменен");
    }
}
