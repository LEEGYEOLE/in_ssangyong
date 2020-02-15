package com.sp.notice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sp.common.FileManager;
import com.sp.common.dao.CommonDAO;

@Service("notice.noticeService")
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private CommonDAO dao;

	@Autowired
	private FileManager fileManager;

	@Override
	public void insertNotice(Notice dto, String pathname) throws Exception {

		try {
			int seq = dao.selectOne("notice.seq");
			dto.setNum(seq);
			
			dao.insertData("notice.insertNotice", dto);
			
			if(!dto.getUpload().isEmpty()) {
				for (MultipartFile mf : dto.getUpload()) {
					String saveFilename = fileManager.doFileUpload(mf, pathname);
					if (saveFilename == null) continue;
					String originalFilename = mf.getOriginalFilename();
					long fileSize = mf.getSize();
					
					dto.setOriginalFilename(originalFilename);
					dto.setSaveFilename(saveFilename);
					dto.setFileSize(fileSize);
					
					insertFile(dto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result =0;
		try {
			result = dao.selectOne("notice.dataCount", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Notice> listNotice(Map<String, Object> map) {
		List<Notice> list = null;
		try {
			list = dao.selectList("notice.listNotice", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Notice> listNoticeTop() {
		List<Notice> list = null;
		try {
			list = dao.selectList("notice.listNoticeTop");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void updateHitCount(int num) throws Exception {
		try {
			dao.selectOne("notice.updateHitCount", num);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public Notice readNotice(int num) {
		Notice dto = null;
		try {
			dto = dao.selectOne("notice.readNotice", num);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return dto;
	}

	@Override
	public Notice preReadNotice(Map<String, Object> map) {
		Notice dto = null;
		try {
			dto = dao.selectOne("notice.preReadNotice", map);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return dto;
	}

	@Override
	public Notice nextReadNotice(Map<String, Object> map) {
		Notice dto = null;
		try {
			dto = dao.selectOne("notice.nextReadNotice", map);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return dto;
	}

	/**
	 * 업데이트 하면서 새로운 파일 업로드를 함께 한다.
	 * 이미 올린 파일은 삭제하거나 추가 할 수 밖에 없다. 파일 수정 불가
	 */
	@Override
	public void updateNotice(Notice dto, String pathname) throws Exception {
		try {
			dao.updateData("notice.updateNotice", dto);
			
			if(!dto.getUpload().isEmpty()) {
				for (MultipartFile mf : dto.getUpload()) {
					String saveFilename = fileManager.doFileUpload(mf, pathname);
					if (saveFilename == null) continue;
					String originalFilename = mf.getOriginalFilename();
					long fileSize = mf.getSize();
					
					dto.setOriginalFilename(originalFilename);
					dto.setSaveFilename(saveFilename);
					dto.setFileSize(fileSize);
					
					insertFile(dto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public void deleteNotice(int num, String pathname) throws Exception {
		try {
			//파일 지우기
			List<Notice> list = listFile(num);
			if(list!=null) {
				for (Notice dto : list) {
					fileManager.doFileDelete(dto.getSaveFilename(), pathname);
				}
			}
			//파일 테이블지우기
			Map<String , Object> map = new HashMap<String, Object>();
			map.put("field", "num");
			map.put("num", num);
			deleteFile(map);

			//게시물지우기
			dao.deleteData("notice.deleteNotice", num);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 원래 안빼고 위의 insertNotice에서 가능...
	 */
	@Override
	public void insertFile(Notice dto) throws Exception {
		try {
			dao.insertData("notice.insertNoticeFile", dto);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Notice> listFile(int num) {
		List<Notice> list =null;
		try {
			list=dao.selectList("notice.listFile", num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Notice readFile(int fileNum) {
		Notice dto = null;
		try {
			dto = dao.selectOne("notice.readFile", fileNum);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return dto;
	}

	@Override
	public void deleteFile(Map<String, Object> map) throws Exception {
		try {
			dao.deleteData("notice.deleteFile", map);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
