import java.util.*;
import java.io.*;

final public class AirlineSystem implements AirlineInterface {
  private String [] cityNames = null;
  private HashMap<String, Integer> st = new HashMap<String,Integer>();
  private Digraph G = null;
  private static final int INFINITY = Integer.MAX_VALUE;
  private static Scanner scan = null;

  /**
  * reads the city names and the routes from a file
  * @param fileName the String file name
  * @return true if routes loaded successfully and false otherwise
  */
  public boolean loadRoutes(String fileName){
    try{
      Scanner fileScan = new Scanner(new FileInputStream(fileName));
      int v = Integer.parseInt(fileScan.nextLine());
      G = new Digraph(v);
  
      cityNames = new String[v];
      for(int i=0; i<v; i++){
        String s = fileScan.nextLine();
        cityNames[i] = s;
        st.put(s, i);
      }
  
      while(fileScan.hasNext()){
        int from = fileScan.nextInt();
        int to = fileScan.nextInt();
        int weight = fileScan.nextInt();
        double price = fileScan.nextDouble();
        G.addEdge(new WeightedDirectedEdge(from-1, to-1, weight, price));
        G.addEdge(new WeightedDirectedEdge(to-1, from-1, weight, price));
        if(fileScan.hasNext())
        {
          fileScan.nextLine();
        }
        
      }
      System.out.println("Data imported successfully.");
      fileScan.close();
      return true;
    } catch (FileNotFoundException e)
    {
      return false;
    }
  }

  /**
  * writes the city names and the routes into a file
  * @param fileName the String file name
  * @return true if routes saved successfully and false otherwise
  */
  public boolean saveRoutes(String fileName){
    return false;
  }


  /**
  * returns the set of city names in the Airline system
  * @return a (possibly empty) Set<String> of city names
  */
  public Set<String> retrieveCityNames(){
    return new HashSet<>(Arrays.asList(cityNames));
  }

  /**
  * returns the set of direct routes out of a given city
  * @param city the String city name
  * @return a (possibly empty) Set<Route> of Route objects representing the
  * direct routes out of city
  * @throws CityNotFoundException if the city is not found in the Airline
  * system
  */
  public Set<Route> retrieveDirectRoutesFrom(String city)
    throws CityNotFoundException{
    if(st.get(city) == null)
    {
      throw new CityNotFoundException("City not found.");
    }
      Set<Route> res = new HashSet<Route>();

      int i = st.get(city);
      for(WeightedDirectedEdge e : G.adj(i))
      {
        res.add(new Route(city, cityNames[e.to()], e.weight, e.price));
        res.add(new Route(cityNames[e.to()], city, e.weight, e.price));
      }
      return  res; 
  }

  /**
  * finds cheapest path(s) between two cities
  * @param source the String source city name
  * @param destination the String destination city name
  * @return a (possibly empty) Set<ArrayList<Route>> of cheapest
  * paths. Each path is an ArrayList<Route> of Route objects that includes a
  * Route out of the source and a Route into the destination.
  * @throws CityNotFoundException if any of the two cities are not found in the
  * Airline system
  */
  public Set<ArrayList<Route>> cheapestItinerary(String source,
    String destination) throws CityNotFoundException{
    return new HashSet<ArrayList<Route>>();
  }


  /**
  * finds cheapest path(s) between two cities going through a third city
  * @param source the String source city name
  * @param transit the String transit city name
  * @param destination the String destination city name
  * @return a (possibly empty) Set<ArrayList<Route>> of cheapest
  * paths. Each path is an ArrayList<Route> of city names that includes
  * a Route out of source, into and out of transit, and into destination.
  * @throws CityNotFoundException if any of the three cities are not found in
  * the Airline system
  */
  public Set<ArrayList<Route>> cheapestItinerary(String source,
    String transit, String destination) throws CityNotFoundException{
      return new HashSet<ArrayList<Route>>();
    }

  /**
   * finds one Minimum Spanning Tree (MST) for each connected component of
   * the graph
   * @return a (possibly empty) Set<Set<Route>> of MSTs. Each MST is a Set<Route>
   * of Route objects representing the MST edges.
   */
  public Set<Set<Route>> getMSTs(){
    return new HashSet<Set<Route>>();
  }

  /**
   * finds all itineraries starting out of a source city and within a given
   * price
   * @param city the String city name
   * @param budget the double budget amount in dollars
   * @return a (possibly empty) Set<ArrayList<Route>> of paths with a total cost
   * less than or equal to the budget. Each path is an ArrayList<Route> of Route
   * objects starting with a Route object out of the source city.
   */
  public Set<ArrayList<Route>> tripsWithin(String city, double budget)
    throws CityNotFoundException {
      return new HashSet<ArrayList<Route>>();
  }

  /**
   * finds all itineraries within a given price regardless of the
   * starting city
   * @param  budget the double budget amount in dollars
   * @return a (possibly empty) Set<ArrayList<Route>> of paths with a total cost
   * less than or equal to the budget. Each path is an ArrayList<Route> of Route
   * objects.
   */
  public Set<ArrayList<Route>> tripsWithin(double budget){
    return new HashSet<ArrayList<Route>>();
  }

  /**
   * delete a given non-stop route from the Airline's schedule. Both directions
   * of the route have to be deleted.
   * @param  source the String source city name
   * @param  destination the String destination city name
   * @return true if the route is deleted successfully and false if no route
   * existed between the two cities
   * @throws CityNotFoundException if any of the two cities are not found in the
   * Airline system
   */
  public boolean deleteRoute(String source, String destination)
    throws CityNotFoundException{
      return false;
    }

  /**
   * delete a given city and all non-stop routes out of and into the city from
   * the Airline schedule.
   * @param  city  the String city name
   * @throws CityNotFoundException if the city is not found in the Airline system
   */
  public void deleteCity(String city) throws CityNotFoundException{
  }


  
  private class Digraph {
    private final int v;
    private int e;
    private LinkedList<WeightedDirectedEdge>[] adj;
    private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path
    private double[] costTo;

    /**
    * Create an empty digraph with v vertices.
    */
    public Digraph(int v) {
      if (v < 0) throw new RuntimeException("Number of vertices must be nonnegative");
      this.v = v;
      this.e = 0;
      @SuppressWarnings("unchecked")
      LinkedList<WeightedDirectedEdge>[] temp =
      (LinkedList<WeightedDirectedEdge>[]) new LinkedList[v];
      adj = temp;
      for (int i = 0; i < v; i++)
        adj[i] = new LinkedList<WeightedDirectedEdge>();
    }
    /**
    * Return the edges leaving vertex v as an Iterable.
    * To iterate over the edges leaving vertex v, use foreach notation:
    * <tt>for (DirectedEdge e : graph.adj(v))</tt>.
    */
    public Iterable<WeightedDirectedEdge> adj(int v) {
      return adj[v];
    }
    public void addEdge(WeightedDirectedEdge edge) {
      int from = edge.from();
      int to = edge.to();
      adj[from].add(edge);
      e++;
    }
    public void dijkstraDistance(int s)
    {
      marked = new boolean[this.v];
      distTo = new int[G.v];
      edgeTo = new int[G.v];
      costTo = new double[G.v];
      for(int i = 0; i < G.v; i++)
      {
        distTo[i] = INFINITY;
        marked[i] = false;
        costTo[i] = 0;
      }
      distTo[s] = 0;
      costTo[s] = 0.0;
      marked[s] = true;
      int nMarked = 1;
      int curr = s;

      while(nMarked < this.v){
        for(WeightedDirectedEdge w: G.adj(curr)){
          if(distTo[curr] + w.weight() < distTo[w.to()])
          {
            distTo[w.to()] = distTo[curr] + w.weight();
            edgeTo[w.to()] = w.from();
            costTo[w.to()] = costTo[curr] + w.price();
          }
        }
      
      int min = INFINITY;
      curr = -1;
      for(int i =0; i < distTo.length;i++)
      {
        if(marked[i])
        {
          continue;
        }
        if(distTo[i]< min){
          min = distTo[i];
          curr = i; 
        }
      }
      if(curr == -1)
      {
        return;
      }
       marked[curr] = true;
    } 

    }
    

    }
    public void bfs(int source) {
      marked = new boolean[this.v];
      distTo = new int[this.v];
      edgeTo = new int[this.v];

      Queue<Integer> q = new LinkedList<Integer>();
      for (int i = 0; i < v; i++){
        distTo[i] = INFINITY;
        marked[i] = false;
      }
      distTo[source] = 0;
      marked[source] = true;
      q.add(source);

      while (!q.isEmpty()) {
        int v = q.remove();
        for (WeightedDirectedEdge w : G.adj(v)) {
          if (!marked[w.to()]) {
            edgeTo[w.w] = v;
            distTo[w.w] = distTo[v]+1;
            marked[w.w] = true;
            q.add(w.w);
          
          }
        }
      }
    }
  }
  

  private class WeightedDirectedEdge {
    private final int v;
    private final int w;
    private int weight;
    private double price;
    /**
    * Create a directed edge from v to w with given weight.
    */
    public WeightedDirectedEdge(int v, int w, int weight, double price) {
      this.v = v;
      this.w = w;
      this.weight = weight;
      this.price = price;
    }

    public int from(){
      return v;
    }

    public int to(){
      return w;
    }

    public int weight(){
      return weight;
    }
    public double price(){
      return price;
    }
  }
}
