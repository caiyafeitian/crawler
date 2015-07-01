package crawler;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class Crawler {

	public static void main(String[] args) {
	
		/*
		 * ����ҳ�澲̬��������ͼƬ
		 */
//		for(int i=1;i<55;i++){
//			String urlString = "";
//			if(i<10)
//				urlString = "http://ipc.chotee.com/uploads/2010/img/485d326b702a_D0C9/clip_image00"+i+".jpg";
//			else {
//				urlString = "http://ipc.chotee.com/uploads/2010/img/485d326b702a_D0C9/clip_image0"+i+".jpg";
//			}
//			try {
//				download(urlString, i+".gif");
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
		
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		
		try {
			/*
			 * ����xpath��������ͼƬ
			 */
//			HtmlPage page = webClient.getPage("http://www.duitang.com/album/65161347/");
//			List<?> hbList = page.getByXPath("//div[@class='mbpho']/a/img");
//			for (int i = 0; i < hbList.size(); i++) {
//				HtmlImage imgHtmlImage = (HtmlImage)hbList.get(i);
//				String imgurlString = imgHtmlImage.getAttribute("src");
//				download(imgurlString, "h"+i+".jpg");
//			}
			/*
			 * ģ���½������������һҳ
			 */
			HtmlPage page = webClient.getPage("https://login.bit.edu.cn/cas/login?service=http%3A%2F%2Fonline.bit.edu.cn%2Fmoodle%2Flogin%2Findex.php");
			HtmlForm form = page.getForms().get(0);
			HtmlTextInput textField = form.getInputByName("username");
			HtmlPasswordInput pass= form.getInputByName("password");
			HtmlElement button = (HtmlElement) page.getByXPath("//*[@id='casLoginForm']/ul/li[4]/input").get(0);
			textField.setValueAttribute("1120101863");  
	        pass.setValueAttribute("1593570");
	        page = button.click();
	        
	        page = webClient.getPage("http://online.bit.edu.cn/moodle/mod/assign/view.php?id=41071&action=grading&group=2385");
	        page = webClient.getPage("http://online.bit.edu.cn/moodle/mod/assign/view.php?id=41071&rownum=0&action=grade");
	        while (true) {
	        	String stateString = ((HtmlElement) page.getByXPath("//*[@id='region-main']/div/div[2]/div/table/tbody/tr[1]/td[2]").get(0)).getTextContent();
		        HtmlForm form1 = (HtmlForm)page.getElementById("mform1");
		        HtmlTextInput textgrade = form1.getInputByName("grade");
		        if (stateString.contains("�ݸ�")||stateString.contains("���ύ")||stateString.contains("�Ѿ��ύ")) 
		        	textgrade.setValueAttribute("100");
		        else 
		        	textgrade.setValueAttribute("0");
		        HtmlSubmitInput submitbtn = form1.getInputByName("saveandshownext");
		        page = submitbtn.click();
//		        System.out.println(page.getUrl().toString());
//				System.out.println(page.asText());
				page = webClient.getPage(page.getUrl().toString());
			}
	        
	        
//	        //����
//	        form = page.getForms().get(0);
//	        textField = form.getInputByName("s");
//	        button = (HtmlSubmitInput)page.getByXPath("//*[@id=\"search\"]/div/div[1]/div[2]/form/div/input[3]").get(0);
//	        textField.setValueAttribute("��Ӱ����");
//	        page = button.click();
//	     
//	        //��һҳ
//	        HtmlAnchor anchor = (HtmlAnchor)page.getByXPath("//*[@id=\"mainContent1\"]/div[4]/div/a[10]").get(0);
//	        page = anchor.click();
//	        System.out.println(page.asText());
	        
	        //webClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void download(String urlString, String filename) throws Exception {
	    // ����URL
	    URL url = new URL(urlString);
	    // ������
	    URLConnection con = url.openConnection();
	    // ������
	    InputStream is = con.getInputStream();
	    // 1K�����ݻ���
	    byte[] bs = new byte[1024];
	    // ��ȡ�������ݳ���
	    int len;
	    // ������ļ���
	    OutputStream os = new FileOutputStream(filename);
	    // ��ʼ��ȡ
	    while ((len = is.read(bs)) != -1) {
	      os.write(bs, 0, len);
	    }
	    // ��ϣ��ر���������
	    os.close();
	    is.close();
	}   

}
