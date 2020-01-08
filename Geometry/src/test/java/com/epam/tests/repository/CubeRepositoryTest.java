package com.epam.tests.repository;

import com.epam.geometry.entity.Cube;
import com.epam.geometry.idgenerator.IdGenerator;
import com.epam.geometry.repository.CubeRepository;
import com.epam.geometry.specification.SearchSpecification;
import com.epam.geometry.specification.SortSpecification;
import com.epam.geometry.specification.sort.ByEdgeLength;
import com.epam.geometry.specification.sort.ByID;
import com.epam.geometry.specification.sort.BySurfaceSquare;
import com.epam.geometry.specification.sort.ByVolume;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CubeRepositoryTest {
    private List<Cube> correctCubes;
    private CubeRepository repository;
    private List<Cube> correctResult;
    private List<Cube> methodResult;

    @Before
    public void initTest() {
        SimpleCubeGenerator generator = new SimpleCubeGenerator();
        // by default it sorted in ascending order in all parameters.
        // in correctCubes cubes are going like cube1<cube2<cube3 ... cubeN-1<cubeN
        correctCubes = generator.generateSimpleCubes(10);
        repository = new CubeRepository();
        repository.addAll(correctCubes);
        correctResult = new ArrayList<>();
        methodResult = null;
        IdGenerator.setZero();
    }

    @Test
    public void shouldReturnCubesWithCorrectId() {
        correctResult.add(correctCubes.get(2)); //in test case index in list is cube ID
        methodResult = repository.query(SearchSpecification.searchById(2)); // 2 is cube ID, that we need
        Assert.assertEquals(correctResult, methodResult);
    }

    @Test
    public void shouldReturnCubesWithIdInRange() {
        correctResult.add(correctCubes.get(1)); //in test case index in list is cube ID
        correctResult.add(correctCubes.get(2));
        methodResult = repository.query(SearchSpecification.searchByIdInRange(1L, 2L));// 1- min, 2 - max
        Assert.assertEquals(correctResult, methodResult);
    }

    @Test
    public void shouldReturnCubesWithCorrectSurfaceSquare() {
        correctResult.add(correctCubes.get(5));
        methodResult = repository.query(SearchSpecification.searchBySurfaceSquare(216d)); // 216d is a surface
        Assert.assertEquals(correctResult, methodResult);                     // square of 5-th element in correctResult
    }

    @Test
    public void shouldReturnCubesWithCorrectSurfaceSquareInRange() {
        correctResult.add(correctCubes.get(3));
        correctResult.add(correctCubes.get(4));
        correctResult.add(correctCubes.get(5));
        methodResult = repository.query(SearchSpecification.searchBySurfaceSquareInRange(96d, 216d)); // 150d and 216d
        Assert.assertEquals(correctResult, methodResult);                     // is surface squares of 3-d - 5-th element
    }                                                                        // in correctCubes

    @Test
    public void shouldReturnCubesWithCorrectVolume() {
        correctResult.add(correctCubes.get(5));
        methodResult = repository.query(SearchSpecification.searchByVolume(216d)); // 125d is volume of fifth cube in
        Assert.assertEquals(correctResult, methodResult);                           // correctCubes
    }

    @Test
    public void shouldReturnCubesWithVolumeInRange() {
        correctResult.add(correctCubes.get(7));
        correctResult.add(correctCubes.get(8));
        methodResult = repository.query(SearchSpecification.searchByVolumeInRange(512d, 729d)); // 294 and 384  is a
        Assert.assertEquals(correctResult, methodResult);// volume of 7-th and 8-th element in correctCubes list
    }

    // In sorting part i'm using correctCubes list as wanted result because it contains sorted  cubes
    // in ascending (each next cube bigger than previous). Each sort method in repository should do the same
    // result. I see no reason to create ready-made presets of cubes sorted in the right order, because it is
    // already in correctCubes
    // shuffle method here shuffles the order in the list
    @Test
    public void shouldSortByEdgeLength() {
        SimpleCubeGenerator generator = new SimpleCubeGenerator();
        generator.shuffleOrder(repository.getCUBES());
        SortSpecification specification = new ByEdgeLength();
        repository.sort(specification);
        Assert.assertEquals(correctCubes, repository.getCUBES());
    }

    @Test
    public void shouldSortById() {
        SimpleCubeGenerator generator = new SimpleCubeGenerator();
        generator.shuffleOrder(repository.getCUBES());
        SortSpecification specification = new ByID();
        repository.sort(specification);
        Assert.assertEquals(correctCubes, repository.getCUBES());
    }

    @Test
    public void shouldSortBySurfaceSquare() {
        SimpleCubeGenerator generator = new SimpleCubeGenerator();
        generator.shuffleOrder(repository.getCUBES());
        SortSpecification specification = new BySurfaceSquare();
        repository.sort(specification);
        Assert.assertEquals(correctCubes, repository.getCUBES());
    }

    @Test
    public void shouldSortByVolume() {
        SimpleCubeGenerator generator = new SimpleCubeGenerator();
        generator.shuffleOrder(repository.getCUBES());
        SortSpecification specification = new ByVolume();
        repository.sort(specification);
        Assert.assertEquals(correctCubes, repository.getCUBES());
    }

    @AfterClass
    public static void endTest() {
        // setting IdGenerator to zero so as not to interfere with other tests
        IdGenerator.setZero();
    }


}
