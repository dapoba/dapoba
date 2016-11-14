import java.sql.SQLException;
import java.util.Scanner;
public class File {

	public String filename;
	private String file_extension;
	public String file_list[]=new String [100];
	database db= new database();
	public void delete_file() throws SQLException
	{
		//정말 지울거냐는 UI띄우기
		//해당 파일 DB에서 지우기
		System.out.println("정말 "+filename+"을 지우시겠습니까?");
		//예라고 선택한다면 삭제 진행
		//아니라면 함수 종료
		Scanner in=new Scanner(System.in);
		String answer=null;
		answer=in.nextLine();
		if(answer=="Y")
		{
			db.database_use();
			String sql="delete ";
			db.stmt.execute(sql);
			db.stmt.close(); 
			db.conn.close(); // close
			System.out.println("파일 삭제가 완료되었습니다.");
			return ;
		}
		else return;
	}
	public String[] select_file() throws SQLException
	{
		//UI파일 검색창에서 파일리스트들 선택
		//문서 홈페이지에 저장함. store_file()호출, 파일 하나 하나에 대한 store_file호출해야함.
		//파일의 이름 리스트 반환함. 
		File file[]=new File[20];
		//UI 파일 탐색창에서 파일 리스트를 받아왔다고 가정함. 
		//filename에 파일 이름 저장함. 
		int i=0;
		while(true)
		{
			file_list[i]="파일 이름 저장하기";
			i++;
			//더이상 선택한 파일이 없을 때까지 게속해서 while문 돌림. 
			//UI구현 후 이 부분 구현할 예정임. 
			if(file_list[i]==null)
				break;
		}
		i=0;
		//file_extension에 확장자 저장함. 
		while(file[i].filename!=null)
		{
			file[i].store_file();
			i++;
		}
		
		return file_list;
	}
	private void store_file() throws SQLException
	{
		//DB에 파일의 이름, 확장자 등을 저장함. 
		//저장완료 UI출력함. 
		db.database_use();
		//sql문 다시 작성=>file테이블 작성
		String sql="insert (into my_table (id, first_name, email) values " + "(1, \"woongjin\", \"finalboogi@naver.com\")"; 		
		db.stmt.execute(sql);
		System.out.println("저장이 완료되었습니다.");
		db.stmt.close(); 
		db.conn.close(); // close
	}
}
