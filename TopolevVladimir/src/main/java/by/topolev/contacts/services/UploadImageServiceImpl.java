package by.topolev.contacts.services;


import static by.topolev.contacts.config.ConfigUtil.*;

import by.topolev.contacts.config.ConfigUtil;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

import java.io.File;

/**
 * Created by Vladimir on 14.09.2016.
 */
public class UploadImageServiceImpl implements UploadImageService {
    private static final Logger LOG = LoggerFactory.getLogger(UploadImageServiceImpl.class);


    @Override
    public String saveImage(FileItem item) {
        LOG.debug("Save profile image");
        createUploadDirIfNotExist();
        if (item == null || item.getName() == null) return null;

        String fileName = getUniqueFileName(item.getName());
        File file = new File(ConfigUtil.getPathUploadProfileImage() + fileName);
        try {
            item.write(file);
            LOG.debug(String.format("Image upload is success, file path = %s", file.getAbsolutePath()));
            return fileName;
        } catch (Exception e) {
            LOG.debug("Problem with upload image", e);
        }
        return null;
    }



    private void createUploadDirIfNotExist() {
        File file = new File(ConfigUtil.getPathUploadProfileImage());
        if (!file.exists()) {
            file.mkdir();
            LOG.debug("Create folder for uploading images: {}", file.getAbsolutePath());
        }
    }

    private String getExpansionFile(String fileName) {
        String[] spliteFileName = fileName.split("\\.");
        if (spliteFileName.length == 2) return spliteFileName[1];
        return "";
    }

    private String getUniqueFileName(String fileName) {
        String exp = getExpansionFile(fileName);
        return UUID.randomUUID().toString().replaceAll("-", "") + "." + exp;
    }


}
