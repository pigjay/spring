package us.codecraft.tinyioc;

public class HelloWorldService {
	
  private String text;	
  
  private OutputService outputService;
	
  public void helloWorld() {
	  System.out.println(text);
	  outputService.output(text);
  }

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}
  
  
}
