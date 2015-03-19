package edu.avans.hartigehap.domain.criteria;

import edu.avans.hartigehap.domain.criteria.filters.PlanningUtil;
import edu.avans.hartigehap.domain.planning.Planning;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CriteriaBuilderTest {

    @Test
    public void testGetInstance() throws Exception {
        CriteriaBuilder builder1 = CriteriaBuilder.getInstance();
        CriteriaBuilder builder2 = CriteriaBuilder.getInstance();
        assertEquals(builder1,builder2);
    }

    @Test
    public void testNot() throws Exception {
        CriteriaBuilder b;
        List<Planning> expected;
        List<Planning> result;

        // all planned
        b = CriteriaBuilder.getInstance();
        b.single(Criteria.Type.PLANNED);
        expected = b.fetch(PlanningUtil.getPlanningList());

        // all NOT planned
        b = CriteriaBuilder.getInstance();
        b.single(b.not(Criteria.Type.PLANNED));
        result = b.fetch(expected);

        // should not have any similar items
        PlanningUtil.assertListsCompletelyDifferent(expected, result);
    }

    @Test
    public void testAnd() throws Exception {

        CriteriaBuilder b;
        List<Planning> expected;
        List<Planning> result;

        // true AND true
        // all items expected to be returned
        b = CriteriaBuilder.getInstance();
        b.and(new AlwaysSucceedsCriteria(),new AlwaysSucceedsCriteria());
        expected = PlanningUtil.getPlanningList();
        result = b.fetch(expected);
        PlanningUtil.assertListsContainSame(expected,result);

        // true AND false
        // no items expected to be returned
        b = CriteriaBuilder.getInstance();
        b.and(new AlwaysSucceedsCriteria(),new AlwaysFailsCriteria());
        expected = PlanningUtil.getPlanningList();
        result = b.fetch(expected);
        PlanningUtil.assertListsCompletelyDifferent(expected, result);

        // false AND false
        // no items expected to be returned
        b = CriteriaBuilder.getInstance();
        b.and(new AlwaysFailsCriteria(),new AlwaysFailsCriteria());
        expected = PlanningUtil.getPlanningList();
        result = b.fetch(expected);
        PlanningUtil.assertListsCompletelyDifferent(expected, result);

    }

    @Test
    public void testAnd1() throws Exception {
        CriteriaBuilder b;
        List<Planning> expected;
        List<Planning> result;

        // for full logical test cases, see testAnd()
        // this is merely to test the Criteria.Type method signature variant

        // true AND true
        // all items expected to be returned
        b = CriteriaBuilder.getInstance();
        b.and(Criteria.Type.PLANNED, Criteria.Type.PLANNED);
        expected = PlanningUtil.getPlanningList();
        result = b.fetch(expected);
        PlanningUtil.assertListsContainSame(expected,result);
    }

    @Test
    public void testOr() throws Exception {
        CriteriaBuilder b;
        List<Planning> expected;
        List<Planning> result;

        // true OR true
        // all items expected to be returned
        b = CriteriaBuilder.getInstance();
        b.or(new AlwaysSucceedsCriteria(), new AlwaysSucceedsCriteria());
        expected = PlanningUtil.getPlanningList();
        result = b.fetch(expected);
        PlanningUtil.assertListsContainSame(expected,result);

        // true OR false
        // all items expected to be returned
        b = CriteriaBuilder.getInstance();
        b.or(new AlwaysSucceedsCriteria(), new AlwaysFailsCriteria());
        expected = PlanningUtil.getPlanningList();
        result = b.fetch(expected);
        PlanningUtil.assertListsContainSame(expected, result);

        // false OR false
        // no items expected to be returned
        b = CriteriaBuilder.getInstance();
        b.or(new AlwaysFailsCriteria(), new AlwaysFailsCriteria());
        expected = PlanningUtil.getPlanningList();
        result = b.fetch(expected);
        PlanningUtil.assertListsCompletelyDifferent(expected, result);
    }

    @Test
    public void testOr1() throws Exception {
        CriteriaBuilder b;
        List<Planning> expected;
        List<Planning> result;

        // for full logical test cases, see testOr()
        // this is merely to test the Criteria.Type method signature variant

        // true OR true
        // all items expected to be returned
        b = CriteriaBuilder.getInstance();
        b.or(Criteria.Type.PLANNED, Criteria.Type.PLANNED);
        expected = PlanningUtil.getPlanningList();
        result = b.fetch(expected);
        PlanningUtil.assertListsContainSame(expected,result);
    }

    @Test
    public void testSingle() throws Exception {
        CriteriaBuilder b;
        List<Planning> expected;
        List<Planning> result;

        // true
        // all items expected to be returned
        b = CriteriaBuilder.getInstance();
        b.single(new AlwaysSucceedsCriteria());
        expected = PlanningUtil.getPlanningList();
        result = b.fetch(expected);
        PlanningUtil.assertListsContainSame(expected, result);

        // false
        // no items expected to be returned
        b = CriteriaBuilder.getInstance();
        b.single(new AlwaysFailsCriteria());
        expected = PlanningUtil.getPlanningList();
        result = b.fetch(expected);
        PlanningUtil.assertListsCompletelyDifferent(expected, result);

    }

    @Test
    public void testSingle1() throws Exception {
        CriteriaBuilder b;
        List<Planning> expected;
        List<Planning> result;

        // for full logical test cases, see testSingle()
        // this is merely to test the Criteria.Type method signature variant

        // true
        // all items expected to be returned
        b = CriteriaBuilder.getInstance();
        b.single(Criteria.Type.PLANNED);
        expected = PlanningUtil.getPlanningList();
        result = b.fetch(expected);
        PlanningUtil.assertListsContainSame(expected, result);
    }

    @Test
    public void testFetch() throws Exception {
        CriteriaBuilder b;
        List<Planning> expected;
        List<Planning> result;

        b = CriteriaBuilder.getInstance();

        // no criteria
        // all items expected to be returned
        expected = PlanningUtil.getPlanningList();
        result = b.fetch(expected);
        PlanningUtil.assertListsContainSame(expected, result);

    }

    private class AlwaysSucceedsCriteria extends Criteria {
        @Override
        public List<Planning> meetCriteria(List<Planning> l) {
            return l;
        }
    }

    private class AlwaysFailsCriteria extends Criteria {
        @Override
        public List<Planning> meetCriteria(List<Planning> l) {
            return new ArrayList<>();
        }
    }
}