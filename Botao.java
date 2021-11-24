package genius;




import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Botao extends JButton {
	
		private Icon normal,apertado;
		private JButton botao;
		private AudioClip som;
		
		
		public Botao(String ico1, String ico2, String enderecoSom){
			//cria o objeto Botao e adicona a ele icones e som
			
			normal = new ImageIcon(ico1);
			apertado = new ImageIcon(ico2);
			botao = new JButton();
			botao.setIcon(normal);
			botao.setPressedIcon(apertado);
			
			try{
				som = java.applet.Applet.newAudioClip(new File(enderecoSom).toURL());
			}catch (MalformedURLException e){  
			}
			
		}
		
		
		public AudioClip getSom() {
			return som;
		}
		
		
		public JButton getBotao() {
			return botao;
		}
		
		
		
		/*Metodos utilizados pelo computador para fazer os botoes piscarem*/
		public void apertaBotao(){
			botao.setIcon(apertado);
			som.play();
		}
		
		
		public void desapertaBotao(){
			botao.setIcon(normal);
			
		}
}
