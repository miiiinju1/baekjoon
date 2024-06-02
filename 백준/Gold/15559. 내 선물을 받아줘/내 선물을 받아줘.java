
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int[] dy = {-1, 0, 0, 1};
    static int[] dx = {0, -1, 1, 0};

    static int[] parent;
    static int[] size;

    static int find(int v) {
        if(parent[v] == v) {
            return v;
        }
        parent[v] = find(parent[v]);
        return parent[v];
    }

    static boolean union(int a, int b) {
        int fa = find(a);
        int fb = find(b);
//        System.out.println("a+\" \"+b = " + a+" "+b);
//        System.out.println("fa + \" \" + fb = " + fa + " " + fb);

        if (fa != fb) {

            if(size[fb]>size[fa]) {
                parent[fa] = fb;
            }
            else {
                parent[fb] = fa;
            }
            return true;
        }
        return false;
    }

    static class Point {
        int y, x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        parent = new int[N * M];
        size = new int[N * M];

        Map<Character, Integer> dir = new HashMap<>();

        dir.put('N', 0);
        dir.put('W', 1);
        dir.put('E', 2);
        dir.put('S', 3);
        for(int i= 0;i<N*M;i++) {
            parent[i] = i;
            size[i] = 1;

        }

        char[][] map = new char[N][M];

        for (int i = 0; i < N; i++) {
            final String str = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = str.charAt(j);
            }
        }

        //탐색 하면서 방문 처리 및, union
        boolean[][] visited = new boolean[N][M];
        Deque<Point> q = new ArrayDeque<>();

        for(int i= 0;i<N;i++) {
            for(int j= 0;j<M;j++) {
                if(!visited[i][j]) {

                    visited[i][j] = true;
                    q.add(new Point(i, j));
//                    System.out.println();
                    while(!q.isEmpty()) {
                        final Point now = q.poll();
//                        System.out.println("now.y+\" \"now.x = " + now.y + " "+now.x);
                        int d = dir.get(map[now.y][now.x]);
                        int y = dy[d] + now.y;
                        int x = dx[d] + now.x;
//                        if(!visited[y][x]) {
                        visited[y][x] = true;
                        q.add(new Point(y, x));
                        if (!union((now.y) * M + now.x, (y) * M + x)) {
                            q = new ArrayDeque<>();
                            break;
                        }

//                        }




                    }




                }

            }
        }
//
//        for(int i = 0;i<N*M;i++) {
//            if(i%M==0)
//                System.out.println();
//            System.out.print(parent[i] + " ");
//        }

        Set<Integer> result = new HashSet<>();
        for(int i = 0;i<N*M;i++) {
            result.add(find(parent[i]));
        }

//        System.out.println(result);
        System.out.println(result.size());






    }
}
