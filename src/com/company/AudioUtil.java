package com.company;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

// handles audio
public class AudioUtil
{
    private static AudioUtil ourInstance = new AudioUtil();

    public static AudioUtil getInstance()
    {
        return ourInstance;
    }

    public AudioUtil() {}

    public URL transform(File audioFile) throws MalformedURLException
    {
        if (audioFile.canRead())
        {
            return audioFile.toURI().toURL();
        }

        throw new IllegalArgumentException();
    }
}
