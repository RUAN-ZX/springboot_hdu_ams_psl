package cn.ryanalexander.psl.service.tool;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseNumDecoder{
    private int courseType;
    private char courseProperty;
    private Integer courseHours;
    private Integer courseCapacity;
    private String courseName;
    private String courseNum;

    String[] numTemp;
    String id; // 课程号

    String academy;
    public CourseNumDecoder(Integer courseHours, Integer courseCapacity, String courseName, String courseNum) {
        this.courseHours = courseHours;
        this.courseCapacity = courseCapacity;
        this.courseName = courseName;
        this.courseNum = courseNum;


        // (2018-2019-2)-20160418-S0403700-1
        // (2018-2019-1)-S0406080-40191-2
        numTemp = courseNum.split("-");

        char firstChar = numTemp[1].charAt(0);

        if(firstChar <= 9){
            this.id = numTemp[4];
            this.courseType = 2;
        }
        else if(firstChar == 'S'){
            this.courseType = 1;
            this.id = numTemp[3];
        }
        else{
            this.courseType = 0;
            this.id = numTemp[3];
        }
        academy = this.id.substring(1,3);

        // get Property
        if(this.courseType == 0) courseProperty = firstChar; // S0401021 S

        if(this.courseName.contains("课程设计")) courseProperty =  'B'; //好像这个权限更高？
        else if(this.courseName.contains("实验") || this.courseName.contains("实践")) courseProperty = 'A';
        else if(this.courseName.contains("实习")) courseProperty = 'J';
        else if(this.courseName.contains("企业")) courseProperty = 'F';
    }

    public String getCourseData(){
        System.out.println(this.id);
        char character = this.id.charAt(7); // 最后一位
        JSONObject result = new JSONObject();
        if(character == 's') result.put("双语", 1.3);
        if(character == 'G') result.put("全英文", 1.5);

        if(academy.equals("18")) result.put("卓越", 1.3);

        return result.toJSONString();
    }

    public double getCapacityFactorByProperty(){
        if(courseType == 0) return Math.min(1 + (courseCapacity - 80) / 200.0, 1.2);

        if(courseProperty == 'A' && courseCapacity > 20) return courseCapacity / 20.0;
        else if(courseProperty == 'J') return courseCapacity <= 30 ? 1.2 : 1.2 * courseCapacity / 30.0 ;
        else if(courseProperty == 'B' && courseCapacity > 40) return courseCapacity / 40.0;
        else if(courseProperty == 'F') return 0.2 * (courseHours / 16) * courseCapacity;
        else return 1.0;
    }
}