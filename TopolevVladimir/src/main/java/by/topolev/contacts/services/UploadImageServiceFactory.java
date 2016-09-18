package by.topolev.contacts.services;

/**
 * Created by Vladimir on 14.09.2016.
 */
public class UploadImageServiceFactory {
    public static UploadImageService getUploadImageService(){
        return new UploadImageServiceImpl();
    }
}
