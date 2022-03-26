package hs.pages.subPages;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import hs.simplefx.Page;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class CalendarView extends Page {

	private Image calendarImg = null; //Image of the calendar to view
	private Rectangle calendarComponent;
	
	/**
	 * Creates a blank calendar view
	 */
	public CalendarView() {
		super();
		calendarComponent = new Rectangle();
		calendarComponent.setLayoutX(0);
		calendarComponent.setLayoutY(0);
		getPane().getChildren().add(calendarComponent);
	}
	
	/**
	 * Updates the calendar which can be viewed
	 * @param img Image of the calendar to be viewed
	 */
	public void updateCalendarImage(BufferedImage img) {
		//Convert buffered image into something JavaFX likes
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "png", out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		calendarImg = new Image(in);
		
		//Replace the calendar view with the new image
		calendarComponent.setWidth(calendarImg.getWidth());
		calendarComponent.setHeight(calendarImg.getHeight());
		calendarComponent.setFill(new ImagePattern(calendarImg));
	}
	
}
