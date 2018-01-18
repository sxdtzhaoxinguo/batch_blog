package com.yida.framework.blog.handler;

import com.yida.framework.blog.handler.input.WordImageCopyHandlerInput;
import com.yida.framework.blog.handler.output.WordImageCopyHandlerOutput;
import com.yida.framework.blog.utils.io.FileUtil;
import com.yida.framework.blog.utils.io.ImageFilenameFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Lanxiaowei
 * @Date 2018-01-18 23:44
 * @Description 复制Word内自包含的图片文件复制到指定目录的任务处理器
 */
public class WordImageCopyHandler implements Handler<WordImageCopyHandlerInput, WordImageCopyHandlerOutput> {
    private ImageFilenameFilter imageFilenameFilter;

    public WordImageCopyHandler() {
        this.imageFilenameFilter = new ImageFilenameFilter();
    }

    @Override
    public void handle(WordImageCopyHandlerInput input, WordImageCopyHandlerOutput output) {
        List<String> unzipFilePaths = input.getUnzipFilePaths();
        if (null != unzipFilePaths && unzipFilePaths.size() > 0) {
            File file = null;
            Map<String, List<String>> imagesMap = new HashMap<String, List<String>>();
            List<String> imagesPreMarkdown = null;
            String actualImagePath = null;
            String imagesNewPath = null;
            for (String unzipFilePath : unzipFilePaths) {
                file = new File(unzipFilePath);
                if (!file.exists() || !file.isDirectory()) {
                    continue;
                }
                String[] images = file.list(this.imageFilenameFilter);
                if (!unzipFilePath.endsWith("/")) {
                    if (!unzipFilePath.endsWith("\\")) {
                        unzipFilePath += "/";
                    }
                }
                imagesPreMarkdown = new ArrayList<String>();
                //图片实际需要复制到的新路径
                imagesNewPath = unzipFilePath + output.MD_IMAGE_BASEPATH;
                //解压后图片的实际路径
                actualImagePath = actualImagePath = unzipFilePath + input.WORD_IMAGE_PATH;
                for (String imageFileName : images) {
                    imagesPreMarkdown.add(imagesNewPath + imageFileName);
                }
                //开始图片复制操作
                FileUtil.copyDirectory(actualImagePath, imagesNewPath, this.imageFilenameFilter);
                imagesMap.put(unzipFilePath, imagesPreMarkdown);
            }
        }
    }
}
