package com.vdreamers.vcompressor.compressor;


import java.io.File;
import java.io.IOException;

/**
 * 压缩器接口
 * <p>
 * date 2019/12/09 16:35:49
 *
 * @author <a href="mailto:codepoetdream@gmail.com">Mr.D</a>
 */
public interface ICompressor {

    /**
     * 获取压缩输出文件
     *
     * @param fileName 指定文件名
     * @param file 压缩前文件名
     * @return 压缩输出文件
     */
    File getCompressOutFile(String fileName, File file);

    /**
     * 压缩
     *
     * @param file 压缩前文件
     * @return 压缩后文件
     * @throws IOException              IOException
     * @throws IllegalArgumentException IllegalArgumentException
     */
    File compress(File file) throws IOException, IllegalArgumentException;
}
