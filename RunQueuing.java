package test;

import java.io.IOException;
import java.util.Comparator;

public class RunQueuing {
	public static int counter = 0;
	public static int time = 0;
	public static int maxWatingTime = 0;
	protected static String outputFile;
	
	public static LinkedList<PrintJob> timePrintJobs1 = new LinkedList<PrintJob>("1", 1); // 시간대별 프린터 작업 링크드 리스트 1
	public static LinkedList<PrintJob> timePrintJobs2 = new LinkedList<PrintJob>("2", 2); // 시간대별 프린터 작업 링크드 리스트 2
	public static LinkedList<PrintJob> timePrintJobs3 = new LinkedList<PrintJob>("3", 3); // 시간대별 프린터 작업 링크드 리스트 3
	public static LinkedList<PrintJob> PrintJobs; // 프린터 작업 링크드 리스트
	public static PriorityQueue<PrintJob> pq; // 프린트 작업 큐 (우선순위 큐)

	public static void main_function(Option option) {
		String file_list[] = option.fileName;

		for (String s : file_list) {
			QueuingJobs(option);
		}
	}

	public static void QueuingJobs(Option option) {
		
		PrintJobs = new LinkedList<PrintJob>("PrintJobs", 1);
		readFileInfo(option);

		pq = new PriorityQueue<PrintJob>(PrintJobs.getSize(), new PrintJobComparator());
		clockTicks(PrintJobs.getFirst().getArrivalTime());
		for (; !PrintJobs.isEmpty();) {
			sendToQueue();
			
			if(true){
				sendToPrint(option);
			}
		}
		
		/*
		if (Integer.parseInt(option.loc) == 1) {
			timePrintJobs1 = new LinkedList<PrintJob>("PrintJobs1", 1);
			readFileInfo(option);

			pq = new PriorityQueue<PrintJob>(timePrintJobs1.getSize(), new PrintJobComparator());
			clockTicks(timePrintJobs1.getFirst().getArrivalTime());
			for (; !timePrintJobs1.isEmpty();) {
				sendToQueue();
				sendToPrint(option);
			}
		} else if (Integer.parseInt(option.loc) == 2) {
			timePrintJobs2 = new LinkedList<PrintJob>("PrintJobs", 2);
			readFileInfo(option);

			pq = new PriorityQueue<PrintJob>(timePrintJobs2.getSize(), new PrintJobComparator());
			clockTicks(timePrintJobs2.getFirst().getArrivalTime());
			for (; !timePrintJobs2.isEmpty();) {
				sendToQueue();
				sendToPrint(option);
			}
		} else {
			timePrintJobs3 = new LinkedList<PrintJob>("PrintJobs", 3);
			readFileInfo(option);

			pq = new PriorityQueue<PrintJob>(timePrintJobs3.getSize(), new PrintJobComparator());
			clockTicks(timePrintJobs3.getFirst().getArrivalTime());
			for (; !timePrintJobs3.isEmpty();) {
				sendToQueue();
				sendToPrint(option);
			}
		}
		*/
		initialize();
	}

	private static void initialize() { // 변수 초기화
		PrintJob.resetId();
		time = 0;
		maxWatingTime = 0;
		PrintJobs = null;
		pq = null;
	}

	private static void readFileInfo(Option option) {
		int previous = 0;

		if (previous <= counter) {
			PrintJobs.insertAtBack(new PrintJob(database.database_page_total(option.fileName), 0,
					(128 - database.database_page_total(option.fileName)), counter));
			previous = counter;
		} else {
			System.err.println("시간 값이 이전 객체 값보다 작음");
			System.exit(-1);
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

private static void sendToPrint(Option option) { // 우선순위 큐잉한 작업들을 프린터로 보내는 함수

     PrintJob pj = null;
     int i=0;
     while (!pq.empty()) {
        String file_path=option.fileName[i];

        Print print = new Print();
        String tmp[] = new String[2];
        tmp = file_path.split(".");
        if(tmp[1].equals("pdf")){
        	print.print_pdf(file_path);
        } else
			try {
				print.print_jpg(file_path);
			} catch (IOException e) {
				e.printStackTrace();
			}

        pj = pq.remove();
        pj.setWaitingTime(time - pj.getArrivalTime());
        if (maxWatingTime < pj.getWaitingTime()) {
           maxWatingTime = pj.getWaitingTime();
        }
        if (!PrintJobs.isEmpty()) {
           if (time >= PrintJobs.getFirst().getArrivalTime())
              break;
        }
        i++;
     }
  }

	/*
	 * 해당 장소에서 인쇄 가능한지 확인하는 함수
	 */
	public boolean checkavailability(int loc) {
		if (timePrintJobs1.getLocation() == loc) {
			if (timePrintJobs1.getSize() >= 50) {
				return false; // 인쇄 불가
			} else
				return true; // 인쇄 가능
		} else if (timePrintJobs2.getLocation() == loc) {
			if (timePrintJobs2.getSize() >= 50) {
				return false; // 인쇄 불가
			} else
				return true; // 인쇄 가능
		} else if (timePrintJobs3.getLocation() == loc) {
			if (timePrintJobs3.getSize() >= 50) {
				return false; // 인쇄 불가
			} else
				return true; // 인쇄 가능
		} else
			return false;
	}

	static class PrintJobComparator implements Comparator<PrintJob> { // Comparator 인터페이스 구현
		public int compare(PrintJob p1, PrintJob p2) {
			return p1.compareTo(p2);
		}
	}

	static class PrintJob implements Comparable<PrintJob> {
		private int id, pageSize, waitingTime, priority, arrivalTime;
		private static int nextId = 0;

		public PrintJob(int pageSize, int waitingTime, int priority, int arrivalTime) // 생성자
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
				System.err.print("사이즈 범위 초과");
				System.exit(-1);
			}
		}

		public int getWaitingTime() {
			return this.waitingTime;
		}

		public void setWaitingTime(int waitingTime) {
			if (waitingTime < 0) {
				System.err.println("오류 : 음수 값 지정");
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
				System.err.println("오류 : 음수 값 지정");
				System.exit(-1);
			} else {
				this.arrivalTime = arrivalTime;
			}
		}

		public int getPriority() {
			return this.priority;
		}

		/*
		 * 우선 순위 = (128 - page size) ,(128 = 최대 인쇄 가능 매수)
		 */
		public void setPriority(int priority) {
			if (priority >= 0 && priority <= 127) {
				this.priority = priority;
			} else {
				System.err.print("우선 순위 범위 초과");
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
		 * 우선 순위 비교
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
		 * 우선 순위가 같은 경우 도착 시간으로 우선 순위 재비교
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

		/*
		 * 링크드리스트 클래스
		 */
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
	 * 공백 리스트 예외 처리 inner class
	 */
	static final class EmptyListException extends RuntimeException {
		public EmptyListException() {
			this("List");
		}

		public EmptyListException(String name) {
			super(name + " is empty"); // superclass 생성자 호출
		}
	}

	/*
	 * priorityQueue inner class
	 * 이 클래스(priorityQueue)는 오픈 소스를 사용하였음을 명시함.
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