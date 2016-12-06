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
		option_select option;

		Job(int id, int size, option_select option) {
			this.id = id;
			page_size = size;
			this.option = option;
		}
	}

	public static void Queuingjobs() {
		int i = 0;

		option_select os = new option_select();
		os = database.database_get_option();
		String[] file = new String[5];
		file = database.database_get_fileName();
		
		os.fileName = file;
		
		Job job = new Job(i, database.database_page_total(), os);

		q.add(job);
		
		while (!q.isEmpty()) {
			Job m = q.poll();
			SendToPrint(m);
			System.out.println("page size : " + m.page_size + " id : " + m.id);
		}
	}//

	public static void SendToPrint(Job job) {
		int i = 0;

		while (job.option.fileName[i] != null) {
			String file_path = job.option.fileName[i++];

			print print = new print();
			String tmp[] = new String[2];
			tmp = file_path.split("\\.");

			//Timer(job.option.time);

			if (tmp[1].equals("pdf")) { // pdf �μ�
				print.print_pdf(job.option);
			} else { // jpg �μ�
				try {
					print.print_image(job.option);
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

			if (tmp2[0].equals(time)) { // ���� �ð��� ���� �ð��� ������ �Լ� ���� �� ����Ʈ �۾� ����
				return;
			}
			try {
				Thread.sleep(1000 * 60); // 1�а� sleep (1�и��� check)
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean checkavailability(int loc) { // ����ť ������ �߰� �ۼ� �ʿ�..
		if (loc == 1) {
			return true;
		} else {
			return false;
		}
	}
}