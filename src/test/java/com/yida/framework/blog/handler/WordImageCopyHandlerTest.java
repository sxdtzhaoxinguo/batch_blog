package com.yida.framework.blog.handler;

import com.yida.framework.blog.handler.input.Word2MarkdownHandlerInput;
import com.yida.framework.blog.handler.input.WordFilterHandlerInput;
import com.yida.framework.blog.handler.input.WordImageCopyHandlerInput;
import com.yida.framework.blog.handler.input.WordUnzipHandlerInput;
import com.yida.framework.blog.handler.output.Word2MarkdownHandlerOutput;
import com.yida.framework.blog.handler.output.WordFilterHandlerOutput;
import com.yida.framework.blog.handler.output.WordImageCopyHandlerOutput;
import com.yida.framework.blog.handler.output.WordUnzipHandlerOutput;
import com.yida.framework.blog.utils.io.DocxFilenameFilter;
import com.yida.framework.blog.utils.io.ImageFilenameFilter;
import com.yida.framework.blog.utils.io.MarkdownFilenameFilter;
import com.yida.framework.blog.utils.io.ZipArchiverFileFilter;

/**
 * @Author Lanxiaowei
 * @Date 2018-02-07 17:29
 * @Description WordImageCopyHandler类的测试用例
 */
public class WordImageCopyHandlerTest {
    public static void main(String[] args) {
        String blogBasePath = "C:/myblog/";
        String blogSendDate = "20180207";
        String pandocPath = "F:/pandoc-2.1/";
        MarkdownFilenameFilter markdownFilenameFilter = new MarkdownFilenameFilter();
        ImageFilenameFilter imageFilenameFilter = new ImageFilenameFilter();
        ZipArchiverFileFilter zipArchiverFileFilter = new ZipArchiverFileFilter();
        DocxFilenameFilter docxFilenameFilter = new DocxFilenameFilter();

        WordFilterHandlerInput wordFilterHandlerInput = new WordFilterHandlerInput();
        WordFilterHandlerOutput wordFilterHandlerOutput = new WordFilterHandlerOutput();
        WordFilterHandler wordFilterHandler = new WordFilterHandler(docxFilenameFilter);
        wordFilterHandler.handle(wordFilterHandlerInput, wordFilterHandlerOutput);
        //打印结果
        System.out.println(wordFilterHandlerOutput);


        Word2MarkdownHandlerInput word2MarkdownHandlerInput = new Word2MarkdownHandlerInput();
        word2MarkdownHandlerInput.setWordFilesName(wordFilterHandlerOutput.getWordFilesPath());
        Word2MarkdownHandlerOutput word2MarkdownHandlerOutput = new Word2MarkdownHandlerOutput();
        Word2MarkdownHandler word2MarkdownHandler = new Word2MarkdownHandler(wordFilterHandler);
        word2MarkdownHandler.handle(word2MarkdownHandlerInput, word2MarkdownHandlerOutput);
        System.out.println(word2MarkdownHandlerOutput);


        WordUnzipHandlerInput wordUnzipHandlerInput = new WordUnzipHandlerInput();
        wordUnzipHandlerInput.setWordFilesName(wordFilterHandlerOutput.getWordFilesPath());
        WordUnzipHandlerOutput wordUnzipHandlerOutput = new WordUnzipHandlerOutput();
        WordUnzipHandler wordUnzipHandler = new WordUnzipHandler(wordFilterHandler, zipArchiverFileFilter);
        wordUnzipHandler.handle(wordUnzipHandlerInput, wordUnzipHandlerOutput);
        System.out.println(wordUnzipHandlerOutput);


        WordImageCopyHandlerInput wordImageCopyHandlerInput = new WordImageCopyHandlerInput();
        wordImageCopyHandlerInput.setUnzipFilePaths(wordUnzipHandlerOutput.getUnzipFilePaths());
        WordImageCopyHandlerOutput wordImageCopyHandlerOutput = new WordImageCopyHandlerOutput();
        WordImageCopyHandler wordImageCopyHandler = new WordImageCopyHandler(wordUnzipHandler, imageFilenameFilter);
        wordImageCopyHandler.handle(wordImageCopyHandlerInput, wordImageCopyHandlerOutput);
        System.out.println(wordImageCopyHandlerOutput);


    }
}
