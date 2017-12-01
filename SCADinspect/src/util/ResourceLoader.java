/*******************************************************************************
 * SCADinspect – https://github.com/ibogicevic/SCADinspect
 * Copyright (C) 2017 Ivan Bogicevic and others
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package util;

import java.io.InputStream;

import gui.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResourceLoader {
	
    private static final String RESOURCES_DIR = "/res/";	
	
    /**
     * Loads a specific icon from the res-folder
     * @return the icon as ImageView
     */
    public static ImageView loadIcon(String fileName) {
        InputStream inputStream = Main.class.getResourceAsStream(RESOURCES_DIR + fileName + ".png");
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
    public static ImageView loadIconSmall(String fileName) {
        ImageView imageView = loadIcon(fileName);
        imageView.setFitHeight(12);
        imageView.setFitWidth(12);
        return imageView;
    }
}
