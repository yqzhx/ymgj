package com.uustop.common.utils.file;

import com.uustop.common.constant.PictureStatusCode;
import com.uustop.common.exception.file.FileNameLengthLimitExceededException;
import com.uustop.common.utils.DateUtils;
import com.uustop.common.utils.ImageUtil;
import com.uustop.framework.config.UUSTOPConfig;
import com.uustop.project.operate.picture.domain.Picture;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * 文件上传工具类
 *
 * @author uustop
 */
public class FileUploadUtils {
    /**
     * 默认大小 50M
     */
    private static final long DEFAULT_MAX_SIZE = 52428800;

    /**
     * 默认上传的地址
     */
    private static String defaultBaseDir = UUSTOPConfig.getProfile();

    /**
     * 默认的文件名最大长度
     */
    private static final int DEFAULT_FILE_NAME_LENGTH = 200;

    /**
     * 默认文件类型jpg
     */
    public static final String IMAGE_JPG_EXTENSION = ".jpg";

    private static int counter = 0;

    public static void setDefaultBaseDir(String defaultBaseDir) {
        FileUploadUtils.defaultBaseDir = defaultBaseDir;
    }

    private static String getDefaultBaseDir() {
        return defaultBaseDir;
    }

    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     */
    public static Picture upload(MultipartFile file) throws IOException {
        try {
            return upload(getDefaultBaseDir(), file, FileUploadUtils.IMAGE_JPG_EXTENSION, 0);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     */
    public static Picture upload(String baseDir, MultipartFile file, Integer type) throws IOException {
        try {
            return upload(baseDir, file, FileUploadUtils.IMAGE_JPG_EXTENSION, type);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir   相对应用的基目录
     * @param file      上传的文件
     * @param extension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException       如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException                          比如读写文件出错时
     */
    public static Picture upload(String baseDir, MultipartFile file, String extension, Integer type)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException {

        int fileNamelength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(file.getOriginalFilename(), fileNamelength, FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file);

        String encodingFilename = encodingFilename(file.getOriginalFilename(), extension);

        String fileName = DateUtils.datePath() + PictureStatusCode.getTypeByVallue(type) + File.separator + encodingFilename;

        File desc = getAbsoluteFile(baseDir, baseDir + fileName);
        file.transferTo(desc);
        //放入系统图片库
        addThumbnail(encodingFilename, baseDir + DateUtils.datePath() + PictureStatusCode.getTypeByVallue(type) + File.separator);
        Picture picture = new Picture();
        picture.setName(encodingFilename);
        picture.setFileSize(String.valueOf(file.getSize()));
        picture.setPicFilepath(baseDir + fileName);
        return picture;
    }

    private static File getAbsoluteFile(String uploadDir, String filename) throws IOException {
        File desc = new File(File.separator + filename);

        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }

    /**
     * 编码文件名
     */
    public static String encodingFilename(String filename, String extension) {
        filename = filename.replace("_", " ");
        filename = new Md5Hash(filename + System.nanoTime() + counter++).toHex().toString() + extension;
        return filename;
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileSizeLimitExceededException 如果超出最大大小
     */
    private static void assertAllowed(MultipartFile file) throws FileSizeLimitExceededException {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE) {
            throw new FileSizeLimitExceededException("not allowed upload upload", size, DEFAULT_MAX_SIZE);
        }
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file) throws IOException
    {
        try
        {
            return upload(baseDir, file, FileUploadUtils.IMAGE_JPG_EXTENSION);
        }
        catch (Exception e)
        {
            throw new IOException(e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @param extension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException 如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException 比如读写文件出错时
     */
    public static final String upload(String baseDir, MultipartFile file, String extension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException
    {

        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH)
        {
            throw new FileNameLengthLimitExceededException(file.getOriginalFilename(), fileNamelength,
                    FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file);

        String fileName = encodingFilename(file.getOriginalFilename(), extension);

        File desc = getAbsoluteFile(baseDir, baseDir + fileName);
        file.transferTo(desc);
        return fileName;
    }


    /**
     * 缩略图
     *
     * @param fileName
     * @param baseUrl
     */
    public static void addThumbnail(String fileName, String baseUrl) {
        String[] strs = fileName.split("\\.");
        ImageUtil.createMinImg(baseUrl + fileName, baseUrl + strs[0] + "_oneLargest." + strs[1], 800, 470);
        ImageUtil.createMinImg(baseUrl + fileName, baseUrl + strs[0] + "_secondLargest." + strs[1], 460, 289);
        ImageUtil.createMinImg(baseUrl + fileName, baseUrl + strs[0] + "_thirdLargest." + strs[1], 345, 220);
        ImageUtil.createMinImg(baseUrl + fileName, baseUrl + strs[0] + "_forthLargest." + strs[1], 316, 195);
        ImageUtil.createMinImg(baseUrl + fileName, baseUrl + strs[0] + "_fifthLargest." + strs[1], 221, 136);
        ImageUtil.createMinImg(baseUrl + fileName, baseUrl + strs[0] + "_sixthLargest." + strs[1], 79, 49);
    }
}
