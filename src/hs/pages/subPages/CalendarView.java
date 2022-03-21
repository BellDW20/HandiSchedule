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

	private Image calendarImg = null;
	private Rectangle calendarComponent;
	
	public CalendarView() {
		super();
//		drawText(300, 292, "No Calendar to View - Empty Schedule");
		calendarComponent = new Rectangle();
		calendarComponent.setLayoutX(0);
		calendarComponent.setLayoutY(0);
		getPane().getChildren().add(calendarComponent);
	}
	
	public void updateCalendarImage(BufferedImage img) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "png", out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
		calendarImg = new Image(in);
		calendarComponent.setWidth(calendarImg.getWidth());
		calendarComponent.setHeight(calendarImg.getHeight());
		calendarComponent.setFill(new ImagePattern(calendarImg));

	}
	
}
