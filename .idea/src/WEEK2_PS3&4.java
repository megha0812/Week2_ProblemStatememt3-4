import java.util.*;

public class FinanceAlgoSuite {

    /* ===================== COMMON MODELS ===================== */

    static class Transaction {
        String id;
        double fee;
        String time;

        Transaction(String id, double fee, String time) {
            this.id = id;
            this.fee = fee;
            this.time = time;
        }

        public String toString() {
            return id + ":" + fee + "@" + time;
        }
    }

    static class Client {
        String name;
        int risk;
        double balance;

        Client(String n, int r, double b) {
            name = n; risk = r; balance = b;
        }

        public String toString() {
            return name + "(" + risk + ")";
        }
    }

    static class Trade {
        int volume;

        Trade(int v) { volume = v; }
    }

    static class Asset {
        String name;
        double returnRate, volatility;

        Asset(String n, double r, double v) {
            name = n; returnRate = r; volatility = v;
        }

        public String toString() {
            return name + ":" + returnRate;
        }
    }

    /* ===================== PROBLEM 1 ===================== */
    static void bubbleSortFees(List<Transaction> list) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    Collections.swap(list, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break; // early stop
        }
    }

    static void insertionSortFeeTime(List<Transaction> list) {
        for (int i = 1; i < list.size(); i++) {
            Transaction key = list.get(i);
            int j = i - 1;

            while (j >= 0 &&
                    (list.get(j).fee > key.fee ||
                            (list.get(j).fee == key.fee &&
                                    list.get(j).time.compareTo(key.time) > 0))) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    static void highFee(List<Transaction> list) {
        list.stream().filter(t -> t.fee > 50)
                .forEach(t -> System.out.println("Outlier: " + t));
    }

    /* ===================== PROBLEM 2 ===================== */
    static void bubbleSortClients(Client[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j].risk > arr[j + 1].risk) {
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    static void insertionSortClientsDesc(Client[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Client key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j].risk < key.risk) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    /* ===================== PROBLEM 3 ===================== */
    static void mergeSort(int[] arr, int l, int r) {
        if (l >= r) return;
        int m = (l + r) / 2;

        mergeSort(arr, l, m);
        mergeSort(arr, m + 1, r);
        merge(arr, l, m, r);
    }

    static void merge(int[] arr, int l, int m, int r) {
        int[] temp = new int[r - l + 1];
        int i = l, j = m + 1, k = 0;

        while (i <= m && j <= r) {
            temp[k++] = (arr[i] <= arr[j]) ? arr[i++] : arr[j++];
        }

        while (i <= m) temp[k++] = arr[i++];
        while (j <= r) temp[k++] = arr[j++];

        System.arraycopy(temp, 0, arr, l, temp.length);
    }

    static void quickSortDesc(int[] arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quickSortDesc(arr, low, p - 1);
            quickSortDesc(arr, p + 1, high);
        }
    }

    static int partition(int[] arr, int low, int high) {
        int pivot = arr[high], i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] > pivot) { // DESC
                i++;
                int t = arr[i]; arr[i] = arr[j]; arr[j] = t;
            }
        }
        int t = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = t;
        return i + 1;
    }

    /* ===================== PROBLEM 4 ===================== */
    static void mergeSortAssets(List<Asset> list) {
        list.sort(Comparator.comparingDouble(a -> a.returnRate)); // stable
    }

    static void quickSortAssets(List<Asset> list) {
        list.sort((a, b) -> {
            if (b.returnRate != a.returnRate)
                return Double.compare(b.returnRate, a.returnRate);
            return Double.compare(a.volatility, b.volatility);
        });
    }

    /* ===================== PROBLEM 5 ===================== */
    static int linearSearch(String[] arr, String key) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i].equals(key)) return i;
        return -1;
    }

    static int binarySearch(String[] arr, String key) {
        int l = 0, h = arr.length - 1;
        while (l <= h) {
            int m = (l + h) / 2;
            if (arr[m].equals(key)) return m;
            if (arr[m].compareTo(key) < 0) l = m + 1;
            else h = m - 1;
        }
        return -1;
    }

    /* ===================== PROBLEM 6 ===================== */
    static int floor(int[] arr, int x) {
        int res = -1;
        for (int v : arr) if (v <= x) res = v;
        return res;
    }

    static int ceil(int[] arr, int x) {
        for (int v : arr) if (v >= x) return v;
        return -1;
    }

    /* ===================== MAIN DEMO ===================== */
    public static void main(String[] args) {

        // Problem 1
        List<Transaction> tx = new ArrayList<>(List.of(
                new Transaction("id1", 10.5, "10:00"),
                new Transaction("id2", 25.0, "09:30"),
                new Transaction("id3", 5.0, "10:15")
        ));

        bubbleSortFees(tx);
        System.out.println("Bubble Sorted Fees: " + tx);

        insertionSortFeeTime(tx);
        System.out.println("Insertion Sorted: " + tx);

        highFee(tx);

        // Problem 2
        Client[] clients = {
                new Client("A", 20, 1000),
                new Client("B", 50, 2000),
                new Client("C", 80, 3000)
        };

        bubbleSortClients(clients);
        System.out.println("Clients Asc: " + Arrays.toString(clients));

        insertionSortClientsDesc(clients);
        System.out.println("Clients Desc: " + Arrays.toString(clients));

        // Problem 3
        int[] trades = {500, 100, 300};
        mergeSort(trades, 0, trades.length - 1);
        System.out.println("Merge Sorted: " + Arrays.toString(trades));

        quickSortDesc(trades, 0, trades.length - 1);
        System.out.println("Quick Desc: " + Arrays.toString(trades));

        // Problem 4
        List<Asset> assets = new ArrayList<>(List.of(
                new Asset("AAPL", 12, 5),
                new Asset("TSLA", 8, 7),
                new Asset("GOOG", 15, 4)
        ));

        mergeSortAssets(assets);
        System.out.println("Assets Merge: " + assets);

        quickSortAssets(assets);
        System.out.println("Assets Quick: " + assets);

        // Problem 5
        String[] logs = {"accA", "accB", "accB", "accC"};
        Arrays.sort(logs);

        System.out.println("Linear: " + linearSearch(logs, "accB"));
        System.out.println("Binary: " + binarySearch(logs, "accB"));

        // Problem 6
        int[] risks = {10, 25, 50, 100};
        System.out.println("Floor(30): " + floor(risks, 30));
        System.out.println("Ceil(30): " + ceil(risks, 30));
    }
}