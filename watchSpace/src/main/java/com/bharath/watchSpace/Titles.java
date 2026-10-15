package com.bharath.watchSpace;

import com.bharath.watchSpace.Entities.Title;
import com.bharath.watchSpace.RepositoryPackage.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class Titles implements CommandLineRunner
{

    @Autowired
    TitleRepository titleRepository;

    @Override
    public void run(String... args) throws Exception
    {
        if (titleRepository.count() > 0) {
            return;
        }

        List<Title> titles = new ArrayList<>();

        Title t1 = new Title();
        t1.setName("Avengers: Endgame");
        t1.setDescription("The Avengers unite to defeat Thanos and restore the universe.");
        t1.setGenre("Action");
        t1.setDurationInMinutes(181);
        titles.add(t1);

        Title t2 = new Title();
        t2.setName("Interstellar");
        t2.setDescription("Astronauts travel through a wormhole to search for a new home for humanity.");
        t2.setGenre("Sci-Fi");
        t2.setDurationInMinutes(169);
        titles.add(t2);

        Title t3 = new Title();
        t3.setName("Inception");
        t3.setDescription("A skilled thief enters people's dreams to steal valuable secrets.");
        t3.setGenre("Sci-Fi");
        t3.setDurationInMinutes(148);
        titles.add(t3);

        Title t4 = new Title();
        t4.setName("The Dark Knight");
        t4.setDescription("Batman faces the Joker, a criminal who threatens Gotham City.");
        t4.setGenre("Action");
        t4.setDurationInMinutes(152);
        titles.add(t4);

        Title t5 = new Title();
        t5.setName("Spider-Man: No Way Home");
        t5.setDescription("Peter Parker faces unexpected consequences when the multiverse opens.");
        t5.setGenre("Action");
        t5.setDurationInMinutes(148);
        titles.add(t5);

        Title t6 = new Title();
        t6.setName("RRR");
        t6.setDescription("Two revolutionaries develop a powerful friendship during British rule in India.");
        t6.setGenre("Action");
        t6.setDurationInMinutes(187);
        titles.add(t6);

        Title t7 = new Title();
        t7.setName("Baahubali: The Beginning");
        t7.setDescription("A young man discovers his royal heritage and an extraordinary destiny.");
        t7.setGenre("Action");
        t7.setDurationInMinutes(159);
        titles.add(t7);

        Title t8 = new Title();
        t8.setName("3 Idiots");
        t8.setDescription("Three engineering students challenge academic pressure and conventional expectations.");
        t8.setGenre("Comedy");
        t8.setDurationInMinutes(170);
        titles.add(t8);

        Title t9 = new Title();
        t9.setName("Drishyam");
        t9.setDescription("A father uses his intelligence to protect his family after a shocking incident.");
        t9.setGenre("Thriller");
        t9.setDurationInMinutes(163);
        titles.add(t9);

        Title t10 = new Title();
        t10.setName("Jurassic Park");
        t10.setDescription("A dinosaur theme park turns dangerous when its security systems fail.");
        t10.setGenre("Adventure");
        t10.setDurationInMinutes(127);
        titles.add(t10);

        titleRepository.saveAll(titles);

    }
}
