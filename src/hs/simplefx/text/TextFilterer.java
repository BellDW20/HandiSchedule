package hs.simplefx.text;

import java.util.ArrayList;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;

public class TextFilterer {

	private ArrayList<TextFilter> filters;
	
	public TextFilterer() {
		this.filters = new ArrayList<>();
	}
	
	public TextFilterer addFilter(TextFilter filter) {
		this.filters.add(filter);
		return this;
	}
	
	@SuppressWarnings("rawtypes")
	public TextFormatter getAsTextFormatter() {
		UnaryOperator<Change> isValidFilter = change -> {
			if(!change.isContentChange()) {
				return change;
			}
			
			String newText = change.getControlNewText();
			
			boolean accepts = true;
			for(TextFilter filter : filters) {
				if(!filter.isAccepted(newText)) {
					accepts = false;
					break;
				}
			}
			
			if(accepts) {
				return change;
			}
			
			return null;
		};
	
		return new TextFormatter<>(isValidFilter);
	}
	
}
