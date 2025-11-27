import gcUnrelatedTypes.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
public class AllListsCouldBeEqual {
    static class MyList<E> implements List<E> {
        @Override
        public boolean add(E o) { return false; }
        @Override
        public void add(int index, E element) { }
        @Override
        public boolean addAll(Collection<? extends E> c) { return false; }
        @Override
        public boolean addAll(int index, Collection<? extends E> c) { return false; }
        @Override
        public void clear() { }
        @Override
        public boolean contains(Object o) { return false; }
        @Override
        public boolean containsAll(Collection<?> c) { return false; }
        @Override
        public E get(int index) { return null; }
        @Override
        public int indexOf(Object o) { return 0; }
        @Override
        public boolean isEmpty() { return false; }
        @Override
        public Iterator<E> iterator() { return null; }
        @Override
        public int lastIndexOf(Object o) { return 0; }
        @Override
        public ListIterator<E> listIterator() { return null; }
        @Override
        public ListIterator<E> listIterator(int index) { return null; }
        @Override
        public boolean remove(Object o) { return false; }
        @Override
        public E remove(int index) { return null; }
        @Override
        public boolean removeAll(Collection<?> c) { return false; }
        @Override
        public boolean retainAll(Collection<?> c) { return false; }
        @Override
        public E set(int index, E element) { return null; }
        @Override
        public int size() { return 0; }
        @Override
        public List<E> subList(int fromIndex, int toIndex) { return null; }
        @Override
        public Object[] toArray() { return null; }
        @Override
        public <T> T[] toArray(T[] a) { return null; }
    }
    public static void main(String arg[]) {
        falsePositive();
        List<Integer> lst = new LinkedList<Integer>();
        List<Integer> lst2 = lst;
        List<ArrayList<String>> mlist = null;
        mlist.contains(new LinkedList<String>());
    }
    public static void falsePositive() {
        ArrayList<Integer> aLst = new ArrayList<Integer>();
        LinkedList<Integer> lLst = new LinkedList<Integer>();
        MyList<Integer> mLst = new MyList<Integer>();
        Set<ArrayList<Integer>> alSet = new HashSet<ArrayList<Integer>>();
        Set<LinkedList<Integer>> llSet = new HashSet<LinkedList<Integer>>();
        System.out.println(aLst.equals(lLst));
        System.out.println(mLst.equals(lLst));
        alSet.contains(lLst);
        alSet.contains(mLst);
        alSet.containsAll(llSet);
    }
}