import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Comparator;

public class RunQueuing {

	public static void main(String args[]) throws IOException {
		File directory = new File("./files");
		String file_list[] = directory.list();

		//database.database_use();

		for (String s : file_list) {
			Algorithm.inputFile = "./files/" + s;
			Algorithm.outputFile = s.substring(0, s.indexOf(".txt")) + "_result.txt";
			Algorithm.QueuingJobs(0);
		}
	}

	static class PrintJobComparator implements Comparator<PrintJob> { // Comparator �������̽� ����
		public int compare(PrintJob p1, PrintJob p2) {
			return p1.compareTo(p2);
		}
	}

	static class PrintJob implements Comparable<PrintJob> {
		private int id, pageSize, waitingTime, priority, arrivalTime;
		private static int nextId = 0;

		public PrintJob(int pageSize, int waitingTime, int priority, int arrivalTime) // ������
		{
			this.id = ++nextId;
			setSize(pageSize);
			setWaitingTime(waitingTime);
			setPriority(priority);
			setArrivalTime(arrivalTime);
		}

		public int getId() {
			return this.id;
		}

		public int getSize() {
			return this.pageSize;
		}

		public void setSize(int size) {
			if (size > 0 && size < 129) {
				this.pageSize = size;
			} else {
				System.err.print("������ ���� �ʰ�");
				System.exit(-1);
			}
		}

		public int getWaitingTime() {
			return this.waitingTime;
		}

		public void setWaitingTime(int waitingTime) {
			if (waitingTime < 0) {
				System.err.println("���� : ���� �� ����");
				System.exit(-1);
			} else {
				this.waitingTime = waitingTime;
			}
		}

		public int getArrivalTime() {
			return this.arrivalTime;
		}

		public void setArrivalTime(int arrivalTime) {
			if (arrivalTime < 0) {
				System.err.println("���� : ���� �� ����");
				System.exit(-1);
			} else {
				this.arrivalTime = arrivalTime;
			}
		}

		public int getPriority() {
			return this.priority;
		}

		public void setPriority(int priority) { // �켱 ���� = (128 - page size) ,(
												// 128�� �ִ� �μ� ���� �ż�)
			if (priority >= 0 && priority <= 127) {
				this.priority = priority;
			} else {
				System.err.print("�켱 ���� ���� �ʰ�");
				System.exit(-1);
			}
		}

		public String toString() {
			return "Id : " + this.id + " Size : " + this.pageSize + " Waiting Time : " + this.waitingTime
					+ " Priority : " + this.priority + " Arrival Time " + this.arrivalTime;
		}

		public static void resetId() {
			nextId = 0;
		}

		/*
		 * �켱 ���� ��
		 */
		public int compareTo(PrintJob p) {
			if (this.getPriority() < p.getPriority()) {
				return -1;
			} else if (this.getPriority() == p.getPriority()) {
				return this.compareSamePriority(p);
			} else {
				return 1;
			}
		}

		/*
		 * �켱 ������ ���� ��� ���� �ð����� �켱 ���� ���
		 */
		private int compareSamePriority(PrintJob p) {
			if (this.getArrivalTime() < p.getArrivalTime())
				return 1;

			else if (this.getArrivalTime() == p.getArrivalTime())
				return 0;

			else
				return -1;
		}
	}

	/*
	 * LinkedList inner class
	 */
	static class LinkedList<T> {
		private class Node<T> {
			T data;
			Node<T> nextNode;

			Node(T data) {
				this(data, null);
			}

			Node(T data, Node<T> node) {
				this.data = data;
				nextNode = node;
			}

			T getObject() {
				return data;
			}

			Node<T> getNext() {
				return nextNode;
			}
		}

		private Node<T> nodeFirst;
		private Node<T> nodeLast;
		private String name;
		private int size;
		private int location;

		public LinkedList(String listName, int location) {
			name = listName;
			nodeFirst = nodeLast = null;
			size = 0;
			this.location = location;
		}

		public T getFirst() {
			return nodeFirst.data;
		}

		public void insertAtFront(T insertItem) {
			Node<T> node = new Node<T>(insertItem);

			if (isEmpty())
				nodeFirst = nodeLast = node;
			else {
				node.nextNode = nodeFirst;
				nodeFirst = node;
			}
			size++;
		}

		public void insertAtBack(T insertItem) {
			Node<T> node = new Node<T>(insertItem);

			if (isEmpty())
				nodeFirst = nodeLast = node;
			else {
				nodeLast.nextNode = node;
				nodeLast = node;
			}
			size++;
		}

		public T removeFromFront() throws EmptyListException {
			if (isEmpty())
				throw new EmptyListException(name);

			T removedItem = nodeFirst.data;

			if (nodeFirst == nodeLast)
				nodeFirst = nodeLast = null;
			else
				nodeFirst = nodeFirst.nextNode;
			size--;
			return removedItem;
		}

		public T removeFromBack() throws EmptyListException {
			if (isEmpty())
				throw new EmptyListException(name);

			T removedItem = nodeLast.data;

			if (nodeFirst == nodeLast)
				nodeFirst = nodeLast = null;
			else {
				Node<T> current = nodeFirst;

				while (current.nextNode != nodeLast)
					current = current.nextNode;

				nodeLast = current;
				current.nextNode = null;
			}
			size--;
			return removedItem;
		}

		public boolean isEmpty() {
			return (nodeFirst == null);
		}

		public int getSize() {
			return size;
		}

		public int getLocation() {
			return location;
		}

		public void print() {
			if (isEmpty()) {
				System.out.printf("Empty %s\n", name);
				return;
			}

			System.out.printf("The %s is: \n", name);
			Node<T> current = nodeFirst;

			while (current != null) {
				System.out.printf("%s \n", current.data);
				current = current.nextNode;
			}
		}
	}

	/*
	 * ���� ����Ʈ ���� ó�� inner class
	 */
	static final class EmptyListException extends RuntimeException {
		public EmptyListException() {
			this("List");
		}

		public EmptyListException(String name) {
			super(name + " is empty"); // superclass ������ ȣ��
		}
	}

	/*
	 * Algorithm inner class
	 */
	static class Algorithm { //
		public static int time = 0;
		public static int maxWatingTime = 0;
		public static double totalWaitingTime = 0.0;
		public static int NumOfCompleted = 0;
		protected static String inputFile;
		protected static String outputFile;
		private static String maxMessage;
		public static LinkedList<LinkedList> timePrintJobs1 = new LinkedList<LinkedList>("1", 1); // �ð��뺰 ��ũ�� ����Ʈ 1
		public static LinkedList<LinkedList> timePrintJobs2 = new LinkedList<LinkedList>("2", 2);; // �ð��뺰 ��ũ�� ����Ʈ 2
		public static LinkedList<LinkedList> timePrintJobs3 = new LinkedList<LinkedList>("3", 3);; // �ð��뺰 ��ũ�� ����Ʈ 3
		public static LinkedList<PrintJob> PrintJobs; // ������ �۾� ��ũ�� ����Ʈ
		public static PriorityQueue<PrintJob> pq; // ����Ʈ �۾� ť (�켱���� ť)

		public static void QueuingJobs(int loc) {

			PrintJobs = new LinkedList<PrintJob>("PrintJobs", loc);
			readFileInfo();

			pq = new PriorityQueue<PrintJob>(PrintJobs.getSize(), new PrintJobComparator());
			clockTicks(PrintJobs.getFirst().getArrivalTime());
			for (; !PrintJobs.isEmpty();) {
				sendToQueue();
				sendToPrint();
			}
			initialize();
		}

		private static void initialize() { // ������ �ʱ�ȭ
			PrintJob.resetId();
			time = 0;
			maxWatingTime = 0;
			totalWaitingTime = 0.0;
			NumOfCompleted = 0;
			maxMessage = "";
			PrintJobs = null;
			pq = null;
		}

		private static void readFileInfo() {
			String line = null;
			String[] tokens = null;
			int previous = 0;
			try {
				BufferedReader br = new BufferedReader(new FileReader(inputFile));
				while ((line = br.readLine()) != null) {
					tokens = line.split(" ");

					if (previous <= Integer.parseInt(tokens[0])) {
						PrintJobs.insertAtBack(new PrintJob(Integer.parseInt(tokens[1]), 0,
								(128 - Integer.parseInt(tokens[1])), Integer.parseInt(tokens[0])));
						previous = Integer.parseInt(tokens[0]);
					} else {
						System.err.println("Error line " + line + "\n (�ð� ���� ���� ��ü ������ ����)");
						System.exit(-1);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private static void clockTicks(int n) {
			time += n;
		}

		private static void sendToQueue() {
			if (time >= PrintJobs.getFirst().getArrivalTime()) {
				while (time >= PrintJobs.getFirst().getArrivalTime()) {
					PrintJob pj = PrintJobs.removeFromFront();
					pj.setWaitingTime(time - pj.getArrivalTime());
					pq.insert(pj);
					if (PrintJobs.isEmpty())
						break;
				}
			} else {
				clockTicks(PrintJobs.getFirst().getArrivalTime() - time);
			}
		}

		private static void sendToPrint() { // �켱���� ť���� �۾����� �����ͷ� ������ �Լ�
			PrintJob pj = null;
			while (!pq.empty()) {
				pj = pq.remove();
				pj.setWaitingTime(time - pj.getArrivalTime());
				totalWaitingTime += pj.getWaitingTime();
				if (maxWatingTime < pj.getWaitingTime()) {
					maxWatingTime = pj.getWaitingTime();
					maxMessage = "�ִ� ��� �ð�  : " + maxWatingTime + " (����  " + pj.getId() + ")";
				}
				completedJob(pj);
				if (!PrintJobs.isEmpty()) {
					if (time >= PrintJobs.getFirst().getArrivalTime())
						break;
				}
			}
		}

		private static void completedJob(PrintJob p) {
			clockTicks(p.getSize());
			writeReport(p);
			NumOfCompleted++;
		}

		private static void writeReport(PrintJob p) {
			BufferedWriter bw = null;
			DecimalFormat df = new DecimalFormat("0.00");
			df.setMinimumFractionDigits(2);
			
			try {
				bw = new BufferedWriter(new FileWriter(outputFile, true));
				bw.write("time = " + time + " (���� " + p.getId() + ")");
				bw.newLine();

				// ����Ʈ�� ť�� ��� �������(��� �۾��� ����� ��������) ��� ���ð��� �ִ� ��� �ð��� ���
				if (PrintJobs.getSize() == 0 && pq.empty()) {
					bw.write("��� ��� �ð� : " + df.format(totalWaitingTime / NumOfCompleted));
					bw.newLine();
					bw.write(maxMessage);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		public boolean checkavailability(int loc) { // �ش� ��ҿ��� �μ� �������� Ȯ���ϴ� �Լ�
			if (timePrintJobs1.getLocation() == loc) {
				if (timePrintJobs1.getSize() >= 50) {
					return false; // �μ� �Ұ�
				} else
					return true; // �μ� ����
			} else if (timePrintJobs2.getLocation() == loc) {
				if (timePrintJobs2.getSize() >= 50) {
					return false; // �μ� �Ұ�
				} else
					return true; // �μ� ����
			} else if (timePrintJobs3.getLocation() == loc) {
				if (timePrintJobs3.getSize() >= 50) {
					return false; // �μ� �Ұ�
				} else
					return true; // �μ� ����
			} else
				return false;
		}
	}

	/*
	 * priorityQueue inner class
	 * �� Ŭ����(priorityQueue)�� ���� �ҽ��� ����Ͽ����� �����.
	 */
	static class PriorityQueue<T> {

		private T[] heap; // Array based heap representation
		private int s; // The number of objects in the heap
		protected Comparator<T> comparator;

		public PriorityQueue(int capacity, Comparator comparator) {
			if (capacity < 1)
				throw new IllegalArgumentException();

			this.heap = (T[]) new Object[capacity + 1];
			this.s = 0;
			this.comparator = comparator;
		}

		public void insert(T object) {
			if (object == null)
				throw new IllegalArgumentException();
			if (s == heap.length) // check available space
				throw new IllegalStateException();
			heap[++s] = object;
			shiftUp(s); // swim new object
		}

		public T remove() {
			// Ensure not empty
			if (s == 0)
				throw new IllegalStateException();
			// Keep a reference to the root object
			T object = heap[1];
			// Replace root object with the one at rightmost leaf
			if (s > 1)
				heap[1] = heap[s];
			// Dispose the rightmost leaf
			heap[s--] = null;
			// Sink the new root element
			shiftDown(1);
			// Return the object removed
			return object;
		}

		private void shiftUp(int i) {
			while (i > 1) { // if i root (i==1) return
				int p = i / 2; // find parent
				int result = comparator.compare(heap[i], heap[p]); // compare parent and child
				if (result <= 0)
					return; // if child <= parent return
				swap(i, p); // else swap and i=p
				i = p;
			}
		}

		private void shiftDown(int i) {
			int l = 2 * i, r = l + 1, max = l;
			// If 2*i >= size, node i is a leaf
			while (l <= s) {
				// Determine the largest children of node i
				if (r <= s) {
					max = comparator.compare(heap[l], heap[r]) < 0 ? r : l;
				}
				// If the heap condition holds, stop. Else swap and go on.
				if (comparator.compare(heap[i], heap[max]) >= 0)
					return;
				swap(i, max);
				i = max;
				l = 2 * i;
				r = l + 1;
				max = l;
			}
		}

		private void swap(int i, int j) {
			T tmp = heap[i];
			heap[i] = heap[j];
			heap[j] = tmp;
		}

		public void print() {
			for (int i = 1; i <= s; i++) {
				System.out.print(heap[i] + "\n");
			}
			System.out.println();
		}

		boolean empty() {
			return s == 0;
		}
	}
}