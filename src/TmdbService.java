import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * Created by Juan Pablo on 31/10/2016.
 */
public interface TmdbService {
    String BASE_URL = "https://api.themoviedb.org/3/";
    String IMAGE_URL = "http://image.tmdb.org/t/p/w154";
    String API_KEY = "f705c06489826188c47e25f982e97f17";

    @GET("movie/{movie_id}")
    Call<Pelicula> getPelicula(@Path("movie_id") Integer id);

    @GET("movie/popular")
    Call<List<Pelicula>> getPeliculasPopulares(@QueryMap Map<String, String> opciones);

    @GET("movie/top_rated")
    Call<List<Pelicula>> getPeliculasMejorRankeadas(@QueryMap Map<String, String> opciones);

    @GET("genre/{genre_id}/movies")
    Call<List<Pelicula>> getPeliculasPorGenero(@Path("genre_id") Integer genero);

}
