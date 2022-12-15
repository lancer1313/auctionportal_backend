package com.example.auctionportal.service;

import com.example.auctionportal.dao.LotDao;
import com.example.auctionportal.dao.LotFileDao;
import com.example.auctionportal.dao.UserDao;
import com.example.auctionportal.dto.LotRequest;
import com.example.auctionportal.dto.LotResponse;
import com.example.auctionportal.dto.MessageResponse;
import com.example.auctionportal.exceptions.FileManagerException;
import com.example.auctionportal.exceptions.NoFileFoundException;
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

    public MessageResponse createLot(LotRequest lotRequest) throws FileManagerException, NoFileFoundException {
        if (lotRequest.getFile() == null) {
            throw new NoFileFoundException("И что вы собираетесь продавать?");
        }
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        MultipartFile file = lotRequest.getFile();
        String fileName = file.getOriginalFilename();
        String filePath = FOLDER_PATH + UUID.randomUUID() + fileName;
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new FileManagerException("Произошла ошибка сохранения файла");
        }
        LotFile lotFile = new LotFile(fileName, file.getContentType(), filePath);
        User user = userDao.findById(userDetails.getId()).get();
        Lot lot = new Lot(lotRequest.getLotTitle(), lotRequest.getLotDescription(),
                Integer.parseInt(lotRequest.getStartingPrice()), Integer.parseInt(lotRequest.getMinimalStep()));
        lot.setFile(lotFile);
        lot.setUser(user);
        lotDao.save(lot);
        lotFileDao.save(lotFile);
        return new MessageResponse("Лот создан");
    }

    public MessageResponse deleteLot(Long id) throws FileManagerException {
        String deleteFilePath = lotDao.findById(id).get().getFile().getFilePath();
        File deleteFile = new File(deleteFilePath);
        if (!deleteFile.delete()) {
            throw new FileManagerException("Ошибка удаления файла");
        }
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

    public MessageResponse redactLot(LotRequest lotRequest, Long id) throws FileManagerException {

        Lot lot = lotDao.findById(id).get();
        lot.setLotTitle(lotRequest.getLotTitle());
        lot.setLotDescription(lotRequest.getLotDescription());
        lot.setStartingPrice(Integer.parseInt(lotRequest.getStartingPrice()));
        lot.setMinimalStep(Integer.parseInt(lotRequest.getMinimalStep()));

        if (lotRequest.getFile() == null) {
            lotDao.save(lot);
            return new MessageResponse("Лот был изменен без трогания файла");
        }
        MultipartFile file = lotRequest.getFile();

        String fileName = file.getOriginalFilename();
        String filePath = FOLDER_PATH + UUID.randomUUID() + fileName;

        LotFile lotFile = lotFileDao.findById(lot.getFile().getId()).get();
        String deleteFilePath = lotFile.getFilePath();
        lotFile.setName(fileName);
        lotFile.setType(file.getContentType());
        lotFile.setFilePath(filePath);

        File deleteFile = new File(deleteFilePath);
        if (!deleteFile.delete()) {
            throw new FileManagerException("Ошибка удаления файла");
        }
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new FileManagerException("Ошибка сохранения файла");
        }
        lotDao.save(lot);
        lot.setFile(lotFile);
        lotFileDao.save(lotFile);
        return new MessageResponse("Лот был изменен");
    }
}
