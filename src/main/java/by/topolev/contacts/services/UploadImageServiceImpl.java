package by.topolev.contacts.services;


import static by.topolev.contacts.config.ConfigUtil.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by Vladimir on 14.09.2016.
 */
public class UploadImageServiceImpl implements  UploadImageService{
    private static final Logger LOG = LoggerFactory.getLogger(UploadImageServiceImpl.class);

    public void uploadImage(){
        LOG.debug("Upload image");
        createUploadDirIfNotExist();


    }

    private void createUploadDirIfNotExist() {
        File file = new File(PATH_UPLOAD_PROFILE_IMAGE);
        if (!file.exists()) {
            file.mkdir();
            LOG.debug("Create folder for uploading images: {}", file.getAbsolutePath());
        }
    }
}
