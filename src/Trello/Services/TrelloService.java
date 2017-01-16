package Trello.Services;

import retrofit2.Call;
import retrofit2.http.*;

import Trello.Card;

import java.util.List;
import java.util.Map;

import static Util.Constants.*;

public interface TrelloService
{

    /**
     * Retrieves all cards of a specific board
     * @param board Board id
     * @param fieldsCommaSeparated fields options to be displayed
     * -> must meet fields Card.class fields
     * @return List<Card>
     * @see Card
    */
    @GET(GET_CARDS)
    Call<List<Card>> getAllCards(
            @Path("board") String board,
            @Query("fields") String fieldsCommaSeparated
    );

    /**
     * Retrieves single card
     * @param card Card id
     * @param fieldsCommaSeparated fields options to be displayed
     * -> must meet fields Card.class fields
     * @return Card
     * @see Card
     */
    @GET(GET_CARD)
    Call<Card> getSingleCard(
            @Path("card") String card,
            @Query("fields") String fieldsCommaSeparated
    );

    /**
     * Retrieves single card, with no fields specification
     * @param card Card id
     * @return Card
     * @see Card
     */
    @GET(GET_CARD)
    Call<Card> getSingleCard(
            @Path("card") String card
    );

    /**
     * Deletes single card
     * @param card Card id
     * @return Card
     * @see Card
     */
    @DELETE(DELETE_CARD)
    Call<Card> deleteSingleCard(
            @Path("card") String card
    );

    /**
     * Adds new card to a specific list
     * @param idList Id of the list
     * @param options fields of the new card in the form of key and values
     * */
    @POST(ADD_CARD)
    Call<Card> insertNewCard(
            @Query("idList") String idList,
            @QueryMap Map<String, String> options

    );

    /**
     * Edits existing card
     * @param card Id of the list
     * @param options fields of the existing card in the form of key and values
     * */
    @PUT(EDIT_CARD)
    Call<Card> editCurrentCard(
            @Path("card") String card,
            @QueryMap Map<String, String> options
    );
}
