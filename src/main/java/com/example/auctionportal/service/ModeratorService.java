package com.example.auctionportal.service;

import com.example.auctionportal.dao.NewsDao;
import com.example.auctionportal.dao.NewsFileDao;
import com.example.auctionportal.dto.MessageResponse;
import com.example.auctionportal.dto.NewsRequest;
import com.example.auctionportal.dto.NewsResponse;
import com.example.auctionportal.exceptions.FileSavingException;
import com.example.auctionportal.exceptions.InvalidFileFormatException;
import com.example.auctionportal.models.News;
import com.example.auctionportal.models.NewsFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    public MessageResponse createNews(NewsRequest newsRequest) throws FileSavingException, InvalidFileFormatException {
        if (newsRequest.getImage().getContentType() != null) {
            MultipartFile image = newsRequest.getImage();
            if (image.getContentType().equalsIgnoreCase("image/png") ||
                    image.getContentType().equalsIgnoreCase("image/jpeg")) {
                String fileName = image.getOriginalFilename();
                String filePath = FOLDER_PATH + UUID.randomUUID() + fileName;
                try {
                    image.transferTo(new File(filePath));
                } catch (IOException e) {
                    throw new FileSavingException("Произошла ошибка сохранения файла");
                }
                NewsFile newsFile = new NewsFile(fileName, image.getContentType(), filePath);
                News news = new News(newsRequest.getTitle(), newsRequest.getText(), false);
                news.setImage(newsFile);
                newsDao.save(news);
                newsFileDao.save(newsFile);
                return new MessageResponse("Новость с картинкой создана");
            }
            throw new InvalidFileFormatException("Недопустимый формат файла");
        }
        News news = new News(newsRequest.getTitle(), newsRequest.getText(), false);
        newsDao.save(news);
        return new MessageResponse("Новость без картинки создана");
    }

    public MessageResponse redactNews(NewsRequest newsRequest, Long id) throws InvalidFileFormatException, FileSavingException {
        if (newsRequest.getImage().getContentType() != null) {
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
                    newsFile.setName(fileName);
                    newsFile.setType(image.getContentType());
                    newsFile.setFilePath(filePath);
                    try {
                        image.transferTo(new File(filePath));
                        // TO DO удаляем изображение в файловой системе
                    } catch (IOException e) {
                        throw new FileSavingException("Ошибка сохранения файла");
                    }
                    newsDao.save(news);
                    news.setImage(newsFile);
                    newsFileDao.save(newsFile);
                    return new MessageResponse("Новость с новой картинкой изменена");
                }
                try {
                    image.transferTo(new File(filePath));
                } catch (IOException e) {
                    throw new FileSavingException("Ошибка сохранения файла");
                }
                newsDao.save(news);
                NewsFile newsFile = new NewsFile(fileName, image.getContentType(), filePath);
                news.setImage(newsFile);
                newsFileDao.save(newsFile);
                return new MessageResponse("Новость теперь с картинкой изменена");
            }
            throw new InvalidFileFormatException("Недопустимый формат файла");
        }
        News news = newsDao.findById(id).get();
        if (news.getImage() != null) {
            Long imageId = news.getImage().getId();
            news.setImage(null);
            newsFileDao.deleteById(imageId);
            news.setTitle(newsRequest.getTitle());
            news.setText(newsRequest.getText());
            news.setRedactered(true);
            newsDao.save(news);
            return new MessageResponse("Новость изменена с убиранием файла");
        }
        news.setTitle(newsRequest.getTitle());
        news.setText(newsRequest.getText());
        news.setRedactered(true);
        newsDao.save(news);
        return new MessageResponse("Новость изменена без добавления картинки");
    }

    // TO DO удаляем изображение в файловой системе
    public MessageResponse deleteNews(Long id) {
        newsDao.deleteById(id);
        return new MessageResponse("Новость с id=" + id + " была удалена");
    }

    public List<NewsResponse> getAllNews() {
        List<News> allNews = newsDao.findAll();
        List<NewsResponse> allNewsResponses = new ArrayList<>();
        for (News news : allNews) {
            allNewsResponses.add(new NewsResponse(news.getId(), news.getTitle(), news.getText(), news.isRedactered(),
                    news.getImage() != null ? news.getImage().getName() : null));
        }
        return allNewsResponses;
    }
}
