import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

    static int N;

    static int[] parent;
    static int[] size;

    static int find(int v) {
        if(parent[v] ==v ) {
            return v;
        }
        parent[v] = find(parent[v]);
        return parent[v];
    }
    static void union(int a, int b) {
        int fa = find(a);
        int fb = find(b);

        if(fa!=fb) {
            size[fa] += size[fb];
            size[fb] = 0;
            parent[fb] = fa;
        }

    }

    static Map<Integer, List<Integer>> map = new HashMap<>();

    static int[] treeParent;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());

        parent = new int[N + 1];
        treeParent = new int[N + 1];
        size = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            map.put(i, new ArrayList<>());
        }
        for (int i = 1; i <= N; i++) {
            parent[i] = i;
        }


        Arrays.fill(size, 1);


        for (int i = 1; i < N; i++) {
            var st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            map.get(a).add(b);
            map.get(b).add(a);
        }

        visited = new boolean[N + 1];
        visited[1] = true;

        dfs(1, 0);
//        for(int i= 0;i<=N;i++) {
//            System.out.print(treeParent[i]+" ");
//        }
//        System.out.println();


        int Q = Integer.parseInt(br.readLine());

        for(int i = 0;i<Q;i++) {
            var st = new StringTokenizer(br.readLine());

            int k = Integer.parseInt(st.nextToken());

            List<Integer> input = new ArrayList<>();

            for (int j = 0; j < k; j++) {
                input.add(Integer.parseInt(st.nextToken()));
            }

            // 시작

            for (int x = 0; x < input.size(); x++) {

                int a = input.get(x);
                for (int y = x + 1; y < input.size(); y++) {
                    int b = input.get(y);
                    if (treeParent[b] == a || treeParent[a] == b) {
                        union(a, b);
                    }
                }
            }

            Map<Integer, Integer> count = new HashMap<>();
            for (Integer num : input) {

                int numParent = find(num);
                int numSize = size[numParent];

                if(numSize==1) continue;

                int size = numSize*(numSize-1)/2;
                count.putIfAbsent(numParent, size);

            }

            int sum = 0;
            for (Integer value : count.values()) {
                sum += value;
            }

            bw.write(sum + "\n");
            for (Integer index : input) {
                parent[index] = index;
                size[index] = 1;
            }

        }
            bw.flush();bw.close();
    }
    static         boolean[] visited ;

    static void dfs(int now, int parent) {
        treeParent[now] = parent;
        for (Integer i : map.get(now)) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(i, now);
            }
        }
    }

}
