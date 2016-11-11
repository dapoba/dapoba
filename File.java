
public class File {

	public String filename;
	private String file_extension;
	public String file_list[]=new String [100];
	public void delete_file()
	{
		//정말 지울거냐는 UI띄우기
		//해당 파일 DB에서 지우기
	}
	public String[] select_file()
	{
		//UI파일 검색창에서 파일리스트들 선택
		//문서 홈페이지에 저장함. store_file()호출, 파일 하나 하나에 대한 store_file호출해야함.
		//파일의 이름 리스트 반환함. 
		String a[]=new String [20];
		return a;
	}
	private void store_file()
	{
		//DB에 파일의 이름, 확장자 등을 저장함. 
		//저장완료 UI출력함. 
		
	}
}
