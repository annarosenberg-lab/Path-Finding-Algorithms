
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.HashMap;


class AStarPathingStrategy implements PathingStrategy {
    public int computeH(Point current, Point des) {
        return (Math.abs(current.x - des.x)) + (Math.abs(current.y - des.y));
    }


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        //create open list
        Queue<AStarNode> openList = new PriorityQueue<AStarNode>();
        //create list of visitedNodes
        Map<Point, AStarNode> visited = new HashMap<Point, AStarNode>();
        //create closed list
        Map<Point, AStarNode> closedList = new HashMap<Point, AStarNode>();
        //create current node
        AStarNode currentNode = new AStarNode(start, 0, computeH(start, end), computeH(start, end), null);
        //add current node to openList
        openList.add(currentNode);


        while (openList.size() != 0) {
            //get currentNode
            for ( AStarNode n : openList){

            }
            currentNode = openList.poll();



            if (withinReach.test(currentNode.getPos(), end)) {
                return traversePath(currentNode);
            }

            //get neighbors for current node
            List<Point> neighbors = potentialNeighbors.apply(currentNode.getPos())
                    .filter(canPassThrough)
                    .collect(Collectors.toList());
            int g;
            g = currentNode.getG() + 1;

            //add neighbors to open or closed lists
            for (Point n : neighbors) {

                //create neighbor node

                int h = computeH(n, end);
                int f = h + g;
                AStarNode neighbor = new AStarNode(n, g, h, f, currentNode);

                if (!visited.containsKey(n) && !closedList.containsKey(n)) {
                    openList.add(neighbor);
                    visited.put(n, neighbor);
                }
                else if (visited.containsKey(n) && !closedList.containsKey(n)) {
                    AStarNode copyNode = visited.get(n);

                    if (copyNode.getG() > neighbor.getG()) {
                        openList.remove(copyNode);
                        openList.add(neighbor);
                        visited.remove(n);
                        visited.put(n, neighbor);
                    }


                }


                closedList.put(currentNode.getPos(), currentNode);
                openList.remove(currentNode);
            }

        }

        return new ArrayList<>();


    }

    public List<Point> traversePath(AStarNode currentNode) {
        List<Point> path = new ArrayList<>();
        while (currentNode.getParent() != null) {
            path.add(currentNode.getPos());
            currentNode = currentNode.getParent();
        }
        Collections.reverse(path);
        return path;
    }
}