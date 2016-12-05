package test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;

public class RunQueuing {
	public static long cur_time;

	public static PriorityQueue<Job> q = new PriorityQueue<Job>(100, new Comparator<Job>() {
		@Override
		public int compare(Job job1, Job job2) {
			if (job1.page_size < job2.page_size)
				return -1;
			else if (job1.page_size > job2.page_size)
				return 1;
			else
				return job1.id - job2.id;
		}
	});

	public static class Job {
		public int id;
		public int page_size;
		Option option;

		Job(int id, int size, Option option) {
			this.id = id;
			page_size = size;
			this.option = option;
		}
	}

	public static void Queuingjobs(Job job) {
		int i = 0;
		int size = 10;

		// Job job = new Job(i, size, new Option());

		while (!q.isEmpty()) {
			Job m = q.poll();
			SendToPrint(m);
			System.out.println(m.page_size + " " + m.id);
		}
	}

	public static void SendToPrint(Job job) {
		int i = 0;

		while (job.option.fileName[i] != null) {
			String file_path = job.option.fileName[i++];

			Print print = new Print();
			String tmp[] = new String[2];
			tmp = file_path.split("\\.");

			Timer(job.option.time);

			if (tmp[1].equals("pdf")) { // pdf 인쇄
				print.print_pdf(file_path);
			} else { // jpg 인쇄
				try {
					print.print_jpg(file_path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void Timer(String time) {
		cur_time = System.currentTimeMillis();
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

		while (true) {
			String date = timeFormat.format(new Date(cur_time));
			String tmp1[] = date.split(" ");
			String tmp2[] = tmp1[1].split(":");

			if (tmp2[0].equals(time)) { // 현재 시각과 예약 시간이 같으면 함수 종료 후 프린트 작업 실행
				return;
			}
			try {
				Thread.sleep(1000 * 60); // 1분간 sleep (1분마다 check)
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean checkavailability(int loc) { // 이중큐 구현시 추가 작성 필요..
		if (loc == 1) {
			return true;
		} else {
			return false;
		}
	}
}