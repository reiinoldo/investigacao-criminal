import java.util.Scanner;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;


public class testtw {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		OAuthService service = new ServiceBuilder()
        .provider(TwitterApi.SSL.class)
        .apiKey("9wCm1u34L3pLVYnvOiFIKPHSi")
        .apiSecret("SoqKvwPy2pi19twgc82qBENGOeuJhCkLQOXpebTbBYXkCdVNLk")
        .build();
		
		 Scanner in = new Scanner(System.in);

		    System.out.println("=== Twitter's OAuth Workflow ===");
		    System.out.println();

		    // Obtain the Request Token
		    System.out.println("Fetching the Request Token...");
		    Token requestToken = service.getRequestToken();
		    System.out.println("Got the Request Token!");
		    System.out.println();

		    System.out.println("Now go and authorize Scribe here:");
		    System.out.println(service.getAuthorizationUrl(requestToken));
		    System.out.println("And paste the verifier here");
		    System.out.print(">>");
		    Verifier verifier = new Verifier(in.nextLine());
		    System.out.println();

		    // Trade the Request Token and Verfier for the Access Token
		    System.out.println("Trading the Request Token for an Access Token...");
		    Token accessToken = service.getAccessToken(requestToken, verifier);
		    System.out.println("Got the Access Token!");
		    System.out.println("(if your curious it looks like this: " + accessToken + " )");
		    System.out.println();

		    // Now let's go and ask for a protected resource!
		    System.out.println("Now we're going to access a protected resource...");
		    OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1/statuses/update.json");
		    request.addBodyParameter("status", "this is sparta! *");
		    service.signRequest(accessToken, request);
		    Response response = request.send();
		    System.out.println("Got it! Lets see what we found...");
		    System.out.println();
		    System.out.println(response.getBody());

		    System.out.println();
		    System.out.println("That's it man! Go and build something awesome with Scribe! :)");
		
		
				
		

	}

}
