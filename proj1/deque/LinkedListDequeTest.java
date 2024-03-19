package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Comparator;
import java.util.Iterator;

import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
		lld1.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

		lld1.addLast("middle");
		assertEquals(2, lld1.size());

		lld1.addLast("back");
		assertEquals(3, lld1.size());

		System.out.println("Printing out deque: ");
		lld1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty
		assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty
		assertFalse("lld1 should contain 1 item", lld1.isEmpty());

		lld1.removeFirst();
		// should be empty
		assertTrue("lld1 should be empty after removal", lld1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);

    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {


        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double>  lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();

    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty deque.Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty deque.Deque,", null, lld1.removeLast());


    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }
    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void LLDequeTest() {

        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 5; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 2; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 4; i > 2; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }
    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void LLDequeConstruction() {
        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>(1);
        lld1.printDeque();
        int n = lld1.get(0);
        assertEquals(1, n);
        //assertEquals(2, lld1.get(0));
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void LLDequeIteration() {
        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addLast(3);
        lld.printDeque();
        for(int i : lld) {
            System.out.println(i + " ");
        }
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void LLDequeEqual() {
        //System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        LinkedListDeque<Integer> lld = new LinkedListDeque<>();
        LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();
        lld.addFirst(1);
        lld.addFirst(2);
        lld.addLast(3);
        assertFalse(lld.equals(lld2));
        lld2.addLast(2);
        lld2.addLast(1);
        lld2.addLast(3);
        assertTrue(lld.equals(lld2));

    }

    @Test
    public void addFirstArrayDeque() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        assertTrue(ad.isEmpty());
        for (int i = 0; i < 10; i++) {
            ad.addFirst(i);
        }
        assertEquals(10, ad.size());
        assertFalse(ad.isEmpty());
        ad.printDeque();
        assertEquals(9, (int) ad.removeFirst());
    }
    @Test
    public void addLastArrayDeque() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();

        for (int i = 0; i < 100; i++) {
            ad.addLast(i);
        }
        assertEquals(100, ad.size());
        //assertEquals(99, (int) ad.removeLast());
        for (int i = 0; i < 100; i++) {
            assertEquals(i, (int) ad.get(i));
        }

    }
    @Test
    public void iteratorArrayDeque() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            ad.addLast(i);
        }

        Iterator<Integer> it = ad.iterator();
        assertEquals(0, (int) it.next());

        for (int e: ad) {
            System.out.print(e + " ");
        }
        //ad.printDeque();
        //assertEquals(99, (int) ad.removeLast());
    }
    @Test
    public void equalArrayDeque() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            ad.addLast(i);
        }
        for (int i = 0; i < 10; i++) {
            ad2.addLast(i);
        }
        assertTrue(ad.equals(ad2));
        ad2.removeLast();
        assertFalse(ad.equals(ad2));
    }
    @Test
    public void resizeArrayDeque() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            ad.addFirst(i);
        }
        for (int i = 0; i < 80; i++) {
            ad.removeLast();
        }
        //assertEquals(1, ad.size());
        assertEquals(99, (int) ad.get(0));
    }

    @Test
    public void randomizedArrayDeque() {
        ArrayDeque<Integer> AD = new ArrayDeque<>();
        LinkedListDeque<Integer> LD = new LinkedListDeque<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 6);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                AD.addLast(randVal);
                LD.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = AD.size();
                int size2 = LD.size();
                assertEquals(size, size2);
                System.out.println("size: " + size);
            } else if (operationNumber == 2 && !AD.isEmpty()) {
                // removeLast
                int last = AD.removeLast();
                int last2 = LD.removeLast();
                assertEquals(last, last2);
                System.out.println("last: " + last);
            } else if (operationNumber == 3 && !LD.isEmpty()) {
                // removeFirst
                int first = AD.removeFirst();
                int first2 = LD.removeFirst();
                assertEquals(first, first2);
                System.out.println("first: " + first);
            } else if (operationNumber == 4) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                AD.addFirst(randVal);
                LD.addFirst(randVal);
                System.out.println("addFirst(" + randVal + ")");
            } else if (operationNumber == 5 && !LD.isEmpty()) {
                // get
                int randIndex = StdRandom.uniform(0, AD.size());
                int adVal = AD.get(randIndex);
                int ldVal = LD.get(randIndex);
                assertEquals(adVal, ldVal);
                System.out.println("get: " + randIndex + " " + adVal +  " " + ldVal);
            }
        }
    }

    @Test
    public void maxArrayDeque() {
        Comparator<String> stringLengthComparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        };
        MaxArrayDeque<String> ad = new MaxArrayDeque<>(stringLengthComparator);
        for (int i = 0; i < 10; i++) {
            String content = "";
            for (int j = 0; j < i; j++){
                content = content.concat("a");
            }
            ad.addFirst(content);
        }
        assertEquals("aaaaaaaaa", ad.max());
        //assertEquals(99, (int) ad.get(0));
    }

    @Test
    public void maxArrayDeque2() {

        MaxArrayDeque<String> ad = new MaxArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            String content = "";
            for (int j = 0; j < i; j++){
                content = content.concat("a");
            }
            ad.addFirst(content);
        }
        Comparator<String> stringLengthComparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        };
        assertEquals("aaaaaaaaa", ad.max(stringLengthComparator));
        //assertEquals(99, (int) ad.get(0));
    }


/*    @Test
    public void timeArrayDeque() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        int n = 10000;
        long[] times = new long[n];
        for (int i = 0; i < n; i++) {
            long statTime = System.nanoTime();
            ad.addFirst(i);
            long endTime = System.nanoTime();
            times[i] = endTime - statTime;
        }

        long averageTime = 0;
        for (long time : times) {
            averageTime += time;
        }
        averageTime /= n;

        long varianceThreshold = 2000;
        for (long time : times) {
            assertTrue(Math.abs(time - averageTime) < varianceThreshold);
        }
    }// not good*/

}
