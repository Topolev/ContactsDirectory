package by.topolev.contacts.services;

import org.apache.commons.fileupload.FileItem;

/**
 * Created by Vladimir on 14.09.2016.
 */
public interface UploadImageService {
    public String saveImage(FileItem item);
}
