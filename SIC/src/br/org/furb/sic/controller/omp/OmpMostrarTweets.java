package br.org.furb.sic.controller.omp;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import twitter4j.Status;
import twitter4j.TwitterException;
import jomp.runtime.OMP;
import br.org.furb.sic.controller.TwitterController;
import br.org.furb.sic.view.Main;

public class OmpMostrarTweets {
	
	private TwitterController tc;
	private List listTweetsFiltrado;
	private HashMap listaCincoUltimosTweets;
	private HashMap listaPerfisFacebook;
	
	public OmpMostrarTweets(List listTweetsFiltrado, HashMap listaCincoUltimosTweets, HashMap listaPerfisFacebook) {
		this.tc = TwitterController.getInstance();
		this.listTweetsFiltrado = listTweetsFiltrado;
		this.listaCincoUltimosTweets = listaCincoUltimosTweets;
		this.listaPerfisFacebook = listaPerfisFacebook;
		
		OMP.setNumThreads(15);
	}
	
	public int mostrarTweets(int qtdeTweetsValidos) {
		
		//int qtdeTweetsValidos = listTweetsFiltrado.size();
		
		//omp parallel
		{
			//omp for reduction(+:qtdeTweetsValidos)
			for (int i = 0; i < listTweetsFiltrado.size(); i++) {
				
				String exibirNaTela = "";
				Status tweet = (Status)listTweetsFiltrado.get(i);
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				exibirNaTela += "Usuario: @" + tweet.getUser().getScreenName() + "\n";
				exibirNaTela += "Descrição: " + tweet.getUser().getDescription() + "\n";
				exibirNaTela += "Localização: " + tweet.getUser().getLocation() + "\n";
				exibirNaTela += "Nome: " + tweet.getUser().getName() + "\n";
				exibirNaTela += sdf.format(tweet.getCreatedAt()) + " - " + tweet.getText() + "\n";

				// pq o hash map?
				// não deveria usar o método cincoUltimosTweetsUsuario()?
				exibirNaTela += listaCincoUltimosTweets.get(tweet.getUser().getId());
				
				/*List cincoUltimosTweetsUsuario = null;
				
				try {
					cincoUltimosTweetsUsuario = tc.cincoUltimosTweetsUsuario(tweet);
					if (cincoUltimosTweetsUsuario != null) {
						exibirNaTela += "[Últimos Tweets]\n";
						for (int j = 0; j < cincoUltimosTweetsUsuario.size(); j++) {
							exibirNaTela += " -> " + ((Status) cincoUltimosTweetsUsuario.get(j)).getText() + "\n";
						}
					}
				} catch (TwitterException e) {
					exibirNaTela += "Falha ao buscar dados do twitter, motivo: consutas excessivas, aguarde alguns instantes e tente novamente.\n";
				} catch (ListaVaziaException e) {
					exibirNaTela += "Nenhum tweet recente.\n";
				}*/

				exibirNaTela += listaPerfisFacebook.get(tweet.getUser().getId());
								
				System.out.println(exibirNaTela);
				qtdeTweetsValidos++;
			}
		}
		return qtdeTweetsValidos;
		/*System.out.println("\n\nQuantidade total de tweets encontrados: " + qtdeTweetsBruto
				           + "\nQuantidade de tweets válidos: " + qtdeTweetsValidos);*/
	}

}
