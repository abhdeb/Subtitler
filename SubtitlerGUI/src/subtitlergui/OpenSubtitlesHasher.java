/*
 * Copyright (c) 2012. Subtitler Renaissance V1.0
 * Renaissance is very simple application to allow search and download subtitles for chosen video and specified language.
 * Copyright (c) Spiral Research 2005-2012(abhra.dev@gmail.com)
 */

package subtitlergui;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.LongBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * Created by IntelliJ IDEA.
 * User: dev
 * Date: Dec 30, 2009
 * Time: 3:37:24 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Hash code is based on Media Player Classic. In natural language it calculates: size + 64bit
 * checksum of the first and last 64k (even if they overlap because the file is smaller than
 * 128k).
 */
public class OpenSubtitlesHasher {

    /**
     * Size of the chunks that will be hashed in bytes (64 KB)
     */
    private static final int HASH_CHUNK_SIZE = 64 * 1024;


    public static String computeHash(File file) throws IOException {
        long size = file.length();
        long chunkSizeForFile = Math.min(HASH_CHUNK_SIZE, size);

        FileChannel fileChannel = new FileInputStream(file).getChannel();

        try {
            long head = computeHashForChunk(fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, chunkSizeForFile));
            long tail = computeHashForChunk(fileChannel.map(FileChannel.MapMode.READ_ONLY, Math.max(size - HASH_CHUNK_SIZE, 0), chunkSizeForFile));

            return String.format("%016x", size + head + tail);
        } finally {
            fileChannel.close();
        }
    }


    public static String computeHash(InputStream stream, long length) throws IOException {

        int chunkSizeForFile = (int) Math.min(HASH_CHUNK_SIZE, length);

        // buffer that will contain the head and the tail chunk, chunks will overlap if length is smaller than two chunks
        byte[] chunkBytes = new byte[(int) Math.min(2 * HASH_CHUNK_SIZE, length)];

        DataInputStream in = new DataInputStream(stream);

        // first chunk
        in.readFully(chunkBytes, 0, chunkSizeForFile);

        long position = chunkSizeForFile;
        long tailChunkPosition = length - chunkSizeForFile;

        // seek to position of the tail chunk, or not at all if length is smaller than two chunks
        while (position < tailChunkPosition && (position += in.skip(tailChunkPosition - position)) >= 0) ;

        // second chunk, or the rest of the data if length is smaller than two chunks
        in.readFully(chunkBytes, chunkSizeForFile, chunkBytes.length - chunkSizeForFile);

        long head = computeHashForChunk(ByteBuffer.wrap(chunkBytes, 0, chunkSizeForFile));
        long tail = computeHashForChunk(ByteBuffer.wrap(chunkBytes, chunkBytes.length - chunkSizeForFile, chunkSizeForFile));

        return String.format("%016x", length + head + tail);
    }


    private static long computeHashForChunk(ByteBuffer buffer) {

        LongBuffer longBuffer = buffer.order(ByteOrder.LITTLE_ENDIAN).asLongBuffer();
        long hash = 0;

        while (longBuffer.hasRemaining()) {
            hash += longBuffer.get();
        }

        return hash;
    }


    public static String ComputeSubtitleHash(String filename) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(filename);
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update((byte) fileInputStream.read());
        byte[] hash = digest.digest();
        System.out.println(hash);
        String s = "";
        for (int i = 0; i < hash.length; i++) {
            s = s + Integer.toHexString(hash[i]);

        }
        System.out.println(s);
        return s;
    }

    public static byte[] unGZip(byte[] compressedData) {
        //
        // Create the decompressor and give it the data to compress
        //
        Inflater decompressor = new Inflater();
        decompressor.setInput(compressedData);

        //
        // Create an expandable byte array to hold the decompressed data
        //
        ByteArrayOutputStream bos = new ByteArrayOutputStream(compressedData.length);

        //
        // Decompress the data
        //
        byte[] buf = new byte[1024];
        while (!decompressor.finished()) {
            try {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            } catch (DataFormatException e) {
            }
        }
        try {
            bos.close();
        } catch (IOException e) {
        }

        //
        // Get the decompressed data
        //
        byte[] decompressedData = bos.toByteArray();

        return decompressedData;

    }

}

