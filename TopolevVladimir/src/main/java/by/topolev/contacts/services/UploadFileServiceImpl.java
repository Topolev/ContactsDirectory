package by.topolev.contacts.services;

import by.topolev.contacts.config.ConfigUtil;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.UUID;


/**
 * Created by Vladimir on 17.09.2016.
 */
public class UploadFileServiceImpl {
    private static final Logger LOG = LoggerFactory.getLogger(UploadImageServiceImpl.class);


    public String saveFile(FileItem item, Integer contactId) {
        LOG.debug("Save attachment for user id = {}", contactId);

        if (item == null || item.getName() == null || "".equals(item.getName())) {
            LOG.debug("User can not attach file");
            return null;
        }

        if (!createUploadDirIfNotExist(contactId)) {
            LOG.debug("Can not save attachment, because can not create nessacary folders.");
            return null;
        }


        String fileName = getUniqueFileName(item.getName());
        File file = new File(ConfigUtil.getPathUploadProfileFiles() + contactId.toString() + "/" + fileName);
        try {
            item.write(file);
            LOG.debug(String.format("File uploading is success, file path = %s", file.getAbsolutePath()));
            return (contactId.toString() + "/" + fileName);
        } catch (Exception e) {
            LOG.debug("Problem with uploading file", e);
        }
        return null;
    }


    private boolean createUploadDirIfNotExist(Integer contactId) {
        return (createDirectory(ConfigUtil.getPathUploadProfileFiles()) && createDirectory(ConfigUtil.getPathUploadProfileFiles() + contactId.toString() + "/"));
    }

    private boolean createDirectory(String nameDirectory) {
        File directory = new File(nameDirectory);
        if (!directory.exists()) {
            if (directory.mkdir()) {
                return true;
            } else {
                LOG.debug("Can not create directory '{}'", directory.getAbsolutePath());
                return false;
            }
        } else {
            return true;
        }
    }

    private String getExpansionFile(String fileName) {
        String[] spliteFileName = fileName.split("\\.");
        return spliteFileName.length == 2 ? spliteFileName[1] : "";
    }

    private String getUniqueFileName(String fileName) {
        String exp = getExpansionFile(fileName);
        return UUID.randomUUID().toString().replaceAll("-", "") + "." + exp;
    }
}
