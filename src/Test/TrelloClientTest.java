package Test;

import Trello.Services.TrelloService;
import Trello.Card;
import Trello.TrelloClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class TrelloClientTest {
    private static int counter = 0;
    private String apiKey;
    private String apiToken;
    private String boardId;
    private String cardId;
    private TrelloService trelloService;
    private String idList;
    private final String commonErrorMessage = "Are you sure you entered correct parameters?";
    @Before
    public void setUp() throws Exception {
        System.out.println("Examining provided parameters ..");
        apiKey = System.getProperty("key");
        apiToken = System.getProperty("token");
        boardId = System.getProperty("boardId"); //qLSlq9UG
        cardId = System.getProperty("cardId"); //587aa99cb1bc510cb88742da
        idList = System.getProperty("idList"); //587aa99cb1bc510cb88742ca
        if(apiKey==null || apiToken==null || boardId==null || cardId==null || idList==null)
        {
            System.err.println("Arguments are missing, please provide:");
            System.out.println("-Dkey=[key] -Dtoken=[token] -DboardId=[boardId] -DcardId=[cardId] -DidList=[idList] ");
            System.exit(0);
        }
        System.out.println("All good, preparing Trello API");
        TrelloClient trelloClient = TrelloClient.getInstance(apiKey, apiToken);
        trelloService = trelloClient.getTrelloService();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("================\nTesting done .."+ ++counter+"\n\n-----------------------");
        apiKey = null;
        apiToken = null;
        boardId = null;
        cardId = null;
        idList = null;
        trelloService = null;
    }

    @Test
    public void getSingleCardTestFilteredByName() throws Exception {
        System.out.println("Trying to get a single card using provided id");
        Call<Card> call = trelloService.getSingleCard(cardId,"name");
        Card card = call.execute().body();

        //Test
        assertEquals(commonErrorMessage,cardId,card.getId());
    }

    @Test
    public void addCardToList() throws Exception {
        System.out.println("Trying to add a single card to a specific list");

        //Check existing list count
        Call<List<Card>> originalCardsCall = trelloService.getAllCards(boardId,"name,pos");
        int cardsCountBefore = originalCardsCall.execute().body().size();

        //Prepare new card
        Map<String,String> options = new HashMap<>();
        options.put("name","Name edited from JUnit!"+(Math.random()));
        options.put("pos","bottom");
        Call<Card> call = trelloService.insertNewCard(idList,options);
        boolean isSuccess = call.execute().isSuccessful();

        //Check existing list new count after adding the card
        Call<List<Card>> newCardsCall = trelloService.getAllCards(boardId,"name,pos");
        int cardsCountAfter = newCardsCall.execute().body().size();

        //Test
        assertEquals(commonErrorMessage,true,isSuccess);
        assertNotEquals(cardsCountBefore,cardsCountAfter);
    }

    @Test
    public void deleteCardFromBoard() throws Exception {
        //Check existing list count
        Call<List<Card>> originalCardsCall = trelloService.getAllCards(boardId,"name,pos");
        List<Card> cards = originalCardsCall.execute().body();
        int cardsCountBefore = cards.size();

        //Check if there is at least one card to delete
            if(cardsCountBefore>0)
            {
                //At least one card detected
                Card cardToBeDeleted = cards.get(0);
                System.out.println("Deleting card: "+cardToBeDeleted.getName());
                Call<Card> deleteCardCall = trelloService.deleteSingleCard(cardToBeDeleted.getId());
                deleteCardCall.execute();
            }
        else
            {
                fail("No cards to delete");
            }

        //Check existing list new count after deleting the card
        Call<List<Card>> newCardsCall = trelloService.getAllCards(boardId,"name,pos");
        int cardsCountAfter = newCardsCall.execute().body().size();

        //Test
        assertNotEquals(cardsCountBefore,cardsCountAfter);

    }

    @Test
    public void editCurrentCard() throws Exception {

        //Prepare fields that will change with values
        Map<String,String> options = new HashMap<>();
        options.put("name","Name edited from JUnit!"+(Math.random()));
        options.put("pos","bottom");

        //Check card's name before editing
        Call<Card> callBeforeEdit = trelloService.getSingleCard(cardId);
        String nameBeforeChange = callBeforeEdit.execute().body().getName();

        //Perform editing
        Call<Card> callToEdit = trelloService.editCurrentCard(cardId,options);
        boolean isSuccess = callToEdit.execute().isSuccessful();

        //Check the same card after change
        Call<Card> callAfterEdit = trelloService.getSingleCard(cardId);
        String nameAfterChange = callAfterEdit.execute().body().getName();

        //Test
        assertEquals(commonErrorMessage,true,isSuccess);
        assertNotEquals(nameBeforeChange,nameAfterChange);

    }
    @AfterClass
    public static void finilize()
    {
        System.out.println("Testing is finished");
    }
}