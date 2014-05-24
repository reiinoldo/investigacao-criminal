package br.org.furb.sic.controller.omp;

import java.util.List;

import br.org.furb.sic.controller.TwitterController;
import jomp.runtime.OMP;
import twitter4j.Status;

public class OmpValidacaoTweet {
	
	private TwitterController tc;
	private List listTweetsFiltrado;
	
	public OmpValidacaoTweet(List listTweetsFiltrado) {
		this.tc = TwitterController.getInstance();
		this.listTweetsFiltrado = listTweetsFiltrado;
	}
	
	public void validaTweets(List listTweetsBruto) {
		OMP.setNumThreads(listTweetsBruto.size());
		
		//omp parallel
		{
			//System.out.println(listTweetsBruto.get(OMP.getThreadNum()));
			//System.out.println("N. da thread = " + OMP.getThreadNum() + "\n tamanho da lista = " + listTweetsBruto.size() + "\n");
			if (tc.isValidTweet((Status)listTweetsBruto.get(OMP.getThreadNum()))) {
				//omp critical
				{
					//System.out.println(listTweetsBruto.get(OMP.getThreadNum()) + "\n");
					listTweetsFiltrado.add((Status)listTweetsBruto.get(OMP.getThreadNum()));
				}
			}
			//System.out.print(OMP.getThreadNum() + ", ");
		}
		//System.out.println("");
	}
}
