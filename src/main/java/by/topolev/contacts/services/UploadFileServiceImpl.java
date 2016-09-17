package by.topolev.contacts.services;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.UUID;

import static by.topolev.contacts.config.ConfigUtil.PATH_UPLOAD_PROFILE_FILES;
import static by.topolev.contacts.config.ConfigUtil.PATH_UPLOAD_PROFILE_IMAGE;

/**
 * Created by Vladimir on 17.09.2016.
 */
public class UploadFileServiceImpl {
    private static final Logger LOG = LoggerFactory.getLogger(UploadImageServiceImpl.class);



    public String saveFile(FileItem item) {
        LOG.debug("Save profile file");
        createUploadDirIfNotExist();
        if (item == null || item.getName() == null || "".equals(item.getName())) return null;

        String fileName = getUniqueFileName(item.getName());
        File file = new File(PATH_UPLOAD_PROFILE_FILES + fileName);
        try {
            item.write(file);
            LOG.debug(String.format("File upload is success, file path = %s", file.getAbsolutePath()));
            return fileName;
        } catch (Exception e) {
            LOG.debug("Problem with upload file", e);
        }
        return null;
    }



    private void createUploadDirIfNotExist() {
        File file = new File(PATH_UPLOAD_PROFILE_FILES);
        if (!file.exists()) {
            file.mkdir();
            LOG.debug("Create folder for uploading files: {}", file.getAbsolutePath());
        }
    }

    private String getExpansionFile(String fileName) {
        String[] spliteFileName = fileName.split("\\.");
        if (spliteFileName.length == 2) return spliteFileName[1];
        return "";
    }

    private String getUniqueFileName(String fileName) {
        String exp = getExpansionFile(fileName);
        String uniqueFileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + exp;
        return uniqueFileName;
    }
}
