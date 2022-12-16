package com.example.auctionportal.service;

import com.example.auctionportal.dao.NewsDao;
import com.example.auctionportal.dao.NewsFileDao;
import com.example.auctionportal.dto.MessageResponse;
import com.example.auctionportal.dto.NewsRequest;
import com.example.auctionportal.dto.NewsTableResponse;
import com.example.auctionportal.dto.NewsTimelineResponse;
import com.example.auctionportal.exceptions.FileManagerException;
import com.example.auctionportal.exceptions.InvalidFileFormatException;
import com.example.auctionportal.models.News;
import com.example.auctionportal.models.NewsFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ModeratorService {

    private final NewsDao newsDao;
    private final NewsFileDao newsFileDao;
    private final String FOLDER_PATH = "D:\\Works Web\\webstorm\\auction-frontendv0.1\\news_images\\";

    public ModeratorService(NewsDao newsDao, NewsFileDao newsFileDao) {
        this.newsDao = newsDao;
        this.newsFileDao = newsFileDao;
    }

    public MessageResponse createNews(NewsRequest newsRequest) throws FileManagerException, InvalidFileFormatException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd - HH:mm:ss");
        String dateAndTime = simpleDateFormat.format(date);

        if (newsRequest.getImage() != null) {
            MultipartFile image = newsRequest.getImage();
            if (image.getContentType().equalsIgnoreCase("image/png") ||
                    image.getContentType().equalsIgnoreCase("image/jpeg")) {
                String fileName = image.getOriginalFilename();
                String filePath = FOLDER_PATH + UUID.randomUUID() + fileName;
                try {
                    image.transferTo(new File(filePath));
                } catch (IOException e) {
                    throw new FileManagerException("Произошла ошибка сохранения файла");
                }
                NewsFile newsFile = new NewsFile(fileName, image.getContentType(), filePath);
                News news = new News(newsRequest.getTitle(), newsRequest.getText(), dateAndTime, false);
                news.setImage(newsFile);
                newsDao.save(news);
                newsFileDao.save(newsFile);
                return new MessageResponse("Новость с картинкой создана");
            }
            throw new InvalidFileFormatException("Недопустимый формат файла");
        }
        News news = new News(newsRequest.getTitle(), newsRequest.getText(), dateAndTime, false);
        newsDao.save(news);
        return new MessageResponse("Новость без картинки создана");
    }

    public MessageResponse redactNews(NewsRequest newsRequest, Long id) throws InvalidFileFormatException, FileManagerException {
        if (newsRequest.getImage() != null) {
            MultipartFile image = newsRequest.getImage();
            if (image.getContentType().equalsIgnoreCase("image/png") ||
                    image.getContentType().equalsIgnoreCase("image/jpeg")) {
                String fileName = image.getOriginalFilename();
                String filePath = FOLDER_PATH + UUID.randomUUID() + fileName;
                News news = newsDao.findById(id).get();
                news.setTitle(newsRequest.getTitle());
                news.setText(newsRequest.getText());
                news.setRedactered(true);
                if (news.getImage() != null) {
                    NewsFile newsFile = newsFileDao.findById(news.getImage().getId()).get();

                    String deleteFilePath = newsFile.getFilePath();
                    File deleteFile = new File(deleteFilePath);
                    if (!deleteFile.delete()) {
                        throw new FileManagerException("Ошибка удаления файла");
                    }

                    newsFile.setName(fileName);
                    newsFile.setType(image.getContentType());
                    newsFile.setFilePath(filePath);

                    try {
                        image.transferTo(new File(filePath));
                    } catch (IOException e) {
                        throw new FileManagerException("Ошибка сохранения файла");
                    }
                    newsDao.save(news);
                    news.setImage(newsFile);
                    newsFileDao.save(newsFile);
                    return new MessageResponse("Новость изменена с сменой изображения");
                }
                try {
                    image.transferTo(new File(filePath));
                } catch (IOException e) {
                    throw new FileManagerException("Ошибка сохранения файла");
                }
                newsDao.save(news);
                NewsFile newsFile = new NewsFile(fileName, image.getContentType(), filePath);
                news.setImage(newsFile);
                newsFileDao.save(newsFile);
                return new MessageResponse("Новость изменена с добавлением изображения");
            }
            throw new InvalidFileFormatException("Недопустимый формат файла");
        }
        News news = newsDao.findById(id).get();
        news.setTitle(newsRequest.getTitle());
        news.setText(newsRequest.getText());
        news.setRedactered(true);
        newsDao.save(news);
        return new MessageResponse("Новость изменена, изображение - нет");
    }

    public MessageResponse redactImage(Long id) {
        News news = newsDao.findById(id).get();
        if (news.getImage() != null) {
            Long imageId = news.getImage().getId();
            news.setImage(null);
            newsFileDao.deleteById(imageId);
            news.setRedactered(true);
            newsDao.save(news);
            return new MessageResponse("Изображение было удалено");
        }
        return new MessageResponse("Нечего удалять, файла не было");
    }

    public MessageResponse deleteNews(Long id) throws FileManagerException {
        NewsFile newsFile = newsDao.findById(id).get().getImage();
        if (newsFile != null) {
            String deleteFilePath = newsFile.getFilePath();
            File deleteFile = new File(deleteFilePath);
            if (!deleteFile.delete()) {
                throw new FileManagerException("Ошибка удаления файла");
            }
        }
        newsDao.deleteById(id);
        return new MessageResponse("Новость с id=" + id + " была удалена");
    }

    public List<NewsTableResponse> getAllNewsTable() {
        List<News> allNews = newsDao.findAll();
        List<NewsTableResponse> allNewsResponseTable = new ArrayList<>();
        for (News news : allNews) {
            allNewsResponseTable.add(new NewsTableResponse(news.getId(), news.getTitle(), news.getText(),
                    news.getDateOfCreation(), news.isRedactered(), news.getImage() != null ? news.getImage().getName() : null));
        }
        return allNewsResponseTable;
    }

    public List<NewsTimelineResponse> getAllNewsTimeLine() {
        List<News> allNews = newsDao.findAll();
        List<NewsTimelineResponse> allNewsResponseTimeline = new ArrayList<>();
        for (News news : allNews) {
            allNewsResponseTimeline.add(new NewsTimelineResponse(news.getTitle(), news.getText(), news.getDateOfCreation(),
                    news.isRedactered(), news.getImage() != null ? news.getImage().getFilePath() : null));
        }
        return allNewsResponseTimeline;
    }
}
