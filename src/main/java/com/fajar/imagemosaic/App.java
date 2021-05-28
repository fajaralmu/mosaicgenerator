package com.fajar.imagemosaic;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.fajar.imagemosaic.config.ConfigLoader;
import com.fajar.imagemosaic.tools.MosaicGenerator;

/**
 * Hello world!
 *
 */
public class App 
{
	static final String INPUT_PATH = ConfigLoader.instance().getInputPath();
    public static void main( String[] args ) throws IOException
    {
        MosaicGenerator.generate(ImageIO.read(new File(INPUT_PATH+"sample.jpg")), true);
        
    }
}
