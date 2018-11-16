package com.cassianomenezes.mytransferwise;

import com.cassianomenezes.mytransferwise.entries.BeerResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BeerssListTest {

    private List<BeerResponse> beerResponseList = new ArrayList<>();

    @Before
    public void startTest() {
        beerResponseList.add(mockBeer());
    }

    @Test
    public void listIsNotNullOrEmpty() {
        Assert.assertNotNull(beerResponseList);
        Assert.assertTrue(!beerResponseList.isEmpty());
        BeerResponse beerResponse = beerResponseList.get(0);
        Assert.assertNotNull(beerResponse.getName());
        Assert.assertNotNull(beerResponse.getDescription());
        Assert.assertNotNull(beerResponse.getFirstBrewed());
        Assert.assertNotNull(beerResponse.getTagline());

    }

    private BeerResponse mockBeer() {
        BeerResponse beerResponse = new BeerResponse();
        beerResponse.setName("Heineken");
        beerResponse.setDescription("Best beerResponse");
        beerResponse.setFirstBrewed("10/1900");
        beerResponse.setTagline("My tagline");

        return beerResponse;
    }
}
