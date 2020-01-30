public class MarlonsCase
{
    public static void main(String [] args)
    {
        ConstrainedTopoSort t = new ConstrainedTopoSort("case01.txt");
        ConstrainedTopoSort t2 = new ConstrainedTopoSort("case02.txt");
        ConstrainedTopoSort t3 = new ConstrainedTopoSort("case03.txt");
        ConstrainedTopoSort t4 = new ConstrainedTopoSort("case04.txt");

        System.out.println(t.hasConstrainedTopoSort(4, 2));
        System.out.println(t2.hasConstrainedTopoSort(4, 2));
        System.out.println(t3.hasConstrainedTopoSort(1,2));
        System.out.println(t4.hasConstrainedTopoSort(4,3));
    }
}
