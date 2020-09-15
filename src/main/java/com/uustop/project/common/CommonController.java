package com.uustop.project.common;

import com.uustop.common.constant.PictureStatusCode;
import com.uustop.common.utils.BaseEntityAutoUtils;
import com.uustop.common.utils.DateUtils;
import com.uustop.common.utils.ImageUtil;
import com.uustop.common.utils.file.FileUploadUtils;
import com.uustop.common.utils.file.FileUtils;
import com.uustop.framework.config.UUSTOPConfig;
import com.uustop.framework.ftp.FtpService;
import com.uustop.framework.web.controller.BaseController;
import com.uustop.framework.web.domain.AjaxResult;
import com.uustop.project.operate.picture.domain.Picture;
import com.uustop.project.operate.picture.service.IPictureService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.uustop.framework.web.domain.AjaxResult.error;
import static com.uustop.framework.web.domain.AjaxResult.success;

/**
 * 通用请求处理
 *
 * @author xh
 */
@Controller
public class CommonController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private IPictureService iPictureService;

    @Autowired
    private FtpService ftpService;

    @RequestMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
        try {
            String filePath = UUSTOPConfig.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=" + setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    private String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }


    /**
     * 多图片上传接口
     */
    @PostMapping("common/uploads")
    @ResponseBody
    public AjaxResult upload(@RequestParam("file") MultipartFile[] files, Integer type) {
        try {
            // 判断file数组不能为空并且长度大于0
            if (files != null && files.length > 0) {
                for (MultipartFile file : files) {
                    // 保存文件
                    return uploadImg(file, type);
                }
            }
            return error();
        } catch (Exception e) {
            log.error("多图片上传失败", e);
            return error("上传图片失败");
        }
    }

    /**
     * 上传接口
     */
    @PostMapping("common/upload")
    @ResponseBody
    public AjaxResult upload(@RequestParam("file") MultipartFile file, Integer type) {
        try {
            if (!file.isEmpty()) {
                return uploadImg(file, type);
            }
            return error();
        } catch (Exception e) {
            log.error("上传失败！", e);
            return error(e.getMessage());
        }
    }


    public AjaxResult uploadImg(MultipartFile file, Integer type) {
        String category = PictureStatusCode.getTypeByVallue(type);
        try {
            String url = UUSTOPConfig.getProfile() + DateUtils.datePath() + category + "/";
            String newUrl = UUSTOPConfig.getProfileUrl() + DateUtils.datePath() + category + "/";
            String fileName = file.getOriginalFilename();
            String suffix = FilenameUtils.getExtension(fileName);
            String prefix = FilenameUtils.removeExtension(fileName);
            String name = FileUploadUtils.encodingFilename(url, FilenameUtils.EXTENSION_SEPARATOR + suffix);
            String newName = FilenameUtils.removeExtension(name);
            addThumbnail(file, newName, suffix, newUrl, file.getSize());

            Picture picture = new Picture();
            picture.setName(name);
            picture.setPicFilepath(newUrl + name);
            picture.setFileSize(String.valueOf(file.getSize()));
            picture.setType(type);
            picture.setOldName(prefix);
            // 缩略图1
            String picFilepath = picture.getPicFilepath();
            String oneLargest = picFilepath.replace(name,
                    newName + "_oneLargest" + FilenameUtils.EXTENSION_SEPARATOR + suffix);
            // 缩略图2
            String secondLargest = picFilepath.replace(name,
                    newName + "_secondLargest" + FilenameUtils.EXTENSION_SEPARATOR + suffix);
            picture.setOneLargest(oneLargest);
            picture.setSecondLargest(secondLargest);
            BaseEntityAutoUtils.autoBaseEntity(picture);

            int res = iPictureService.insertPicture(picture);
            return res > 0 ? success(picture) : error();
        } catch (Exception e) {
            log.error("多图片上传失败", e);
            return error("上传图片失败");
        }
    }

    /**
     * 缩略图
     *
     * @param suffix  文件名后缀
     * @param prefix  文件名前缀
     * @param baseUrl 上传路径
     */
    private void addThumbnail(MultipartFile file, String prefix, String suffix, String baseUrl, Long fileSize) throws IOException {
        ftpService.uploadFile(baseUrl, prefix + "." + suffix, file.getInputStream());
        if (fileSize > 600 * 1024) {
            BufferedImage bufferedImage = ImageUtil.createMinIoImg(file.getInputStream(), 1200, 1000);
            InputStream inputStream = FileUtils.imageToInputStream(bufferedImage, suffix);
            ftpService.uploadFile(baseUrl, prefix + "_oneLargest." + suffix, inputStream);
            inputStream.close();

            BufferedImage bufferedImage1 = ImageUtil.createMinIoImg(file.getInputStream(), 800, 470);
            InputStream inputStream1 = FileUtils.imageToInputStream(bufferedImage1, suffix);
            ftpService.uploadFile(baseUrl, prefix + "_secondLargest." + suffix, inputStream1);
            inputStream1.close();

            BufferedImage bufferedImage2 = ImageUtil.createMinIoImg(file.getInputStream(), 460, 289);
            InputStream inputStream2 = FileUtils.imageToInputStream(bufferedImage2, suffix);
            ftpService.uploadFile(baseUrl, prefix + "_thirdLargest." + suffix, inputStream2);
            inputStream2.close();

            BufferedImage bufferedImage3 = ImageUtil.createMinIoImg(file.getInputStream(), 316, 195);
            InputStream inputStream3 = FileUtils.imageToInputStream(bufferedImage3, suffix);
            ftpService.uploadFile(baseUrl, prefix + "_forthLargest." + suffix, inputStream3);
            inputStream3.close();

            BufferedImage bufferedImage4 = ImageUtil.createMinIoImg(file.getInputStream(), 221, 136);
            InputStream inputStream4 = FileUtils.imageToInputStream(bufferedImage4, suffix);
            ftpService.uploadFile(baseUrl, prefix + "_fifthLargest." + suffix, inputStream4);
            inputStream4.close();


            BufferedImage bufferedImage5 = ImageUtil.createMinIoImg(file.getInputStream(), 79, 49);
            InputStream inputStream5 = FileUtils.imageToInputStream(bufferedImage5, suffix);
            ftpService.uploadFile(baseUrl, prefix + "_sixthLargest." + suffix, inputStream5);
            inputStream5.close();

        } else {
            BufferedImage bufferedImage = ImageUtil.createMinIoImg(file.getInputStream(), 1200, 1000);
            InputStream inputStream = FileUtils.imageToInputStream(bufferedImage, suffix);
            ftpService.uploadFile(baseUrl, prefix + "_oneLargest." + suffix, inputStream);
            inputStream.close();

            BufferedImage bufferedImage1 = ImageUtil.createMinIoImg(file.getInputStream(), 800, 470);
            InputStream inputStream1 = FileUtils.imageToInputStream(bufferedImage1, suffix);
            ftpService.uploadFile(baseUrl, prefix + "_secondLargest." + suffix, inputStream1);
            inputStream1.close();

            BufferedImage bufferedImage5 = ImageUtil.createMinIoImg(file.getInputStream(), 79, 49);
            InputStream inputStream5 = FileUtils.imageToInputStream(bufferedImage5, suffix);
            ftpService.uploadFile(baseUrl, prefix + "_sixthLargest." + suffix, inputStream5);
            inputStream5.close();
        }
    }

}
