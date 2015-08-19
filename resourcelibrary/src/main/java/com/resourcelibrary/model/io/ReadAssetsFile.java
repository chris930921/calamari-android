package com.resourcelibrary.model.io;

import android.content.Context;
import android.content.res.AssetManager;

import com.resourcelibrary.model.log.ShowLog;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by User on 2015/8/9.
 */
public class ReadAssetsFile {
    private AssetManager assetManager;

    public ReadAssetsFile(Context context) {
        this.assetManager = context.getAssets();
    }

    public String readText(String filePath) {
        String result = "";
        try {
            InputStream inputStream = assetManager.open(filePath);
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[1024];
            while (reader.read(buffer) != -1) {
                stringBuilder.append(buffer);
            }
            result = stringBuilder.toString().replace("\n", "").replace("\r", "").trim();
        } catch (IOException e) {
            ShowLog.d("ReadAssetsFile", e);
            e.printStackTrace();
        }
        return result;
    }
}
