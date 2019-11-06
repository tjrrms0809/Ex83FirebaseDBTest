package com.ahnsafety.ex83firebasedbtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //처음 작은 Firebase console에서
    //[프로젝트 만들기 or 프로젝트 추가]를 통해
    //작업순서[workflow]대로 이 프로젝트와
    //firebase를 연결하기!!

    EditText et;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et= findViewById(R.id.et);
        tv= findViewById(R.id.tv);
    }

    public void clickSave(View view) {

        //EditText에 있는 글씨 얻어오기
        String data= et.getText().toString();

        //Firebase 실시간 데이터베이스에 저장..

        //1. Firebase실시간 DB관리 객체 얻어오기
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();

        //2. 저장시킬 노드 참조객체 가져오기
        DatabaseReference rootRef= firebaseDatabase.getReference();//최상위 노드

        //각 노드에 값 대입해보기..

        //1)별도의 키[key:식별자]없이 값[value]만 저장하기
//        rootRef.setValue(data);
//
//        //저장된 값 읽어오기..
//        //별도의 읽어오기 버튼이 없음...
//        //실시간 DB인 만큼
//        //데이터베이스 변경이 발생하면 이에 반응하는
//        //리스너를 통해 실시간 DB를 읽어옴
//        rootRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                //파라미턴 전달되어 온 DataSnapshot객체를 통해 데이터를 가져올 수 있음.
//                //String data= (String)dataSnapshot.getValue();
//
//                //형변환이 귀찮으면...getValue()의 매개변수로 자료형.class를 지정
//                String data= dataSnapshot.getValue(String.class);
//                tv.setText(data);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        //값이 변경됨. 누적되어 저장되지 않음.
        //2) 누적하여 값 저장하기
//        DatabaseReference childRef= rootRef.push();//push() : 자식노드 추가
//        childRef.setValue(data);
//
//        rootRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                //String data= dataSnapshot.getValue(String.class);//ERROR
//                //최상위 노드가 데이터를 가지고 있지 않기에 String으로 리턴받으면 에러
//                //즉, 자식노드들이 여러개 이므로
//                //자식노드들로 부터 데이터를 가져와야 함.
//
//                StringBuffer buffer= new StringBuffer();
//                for( DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    String data= snapshot.getValue(String.class);
//                    buffer.append(data+"\n");
//                }
//
//                tv.setText(buffer.toString());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        //3) 자식노드들의 식별 키값을 부여하면서 값 저장
//        DatabaseReference dataRef= rootRef.child("data");//최상위 노드에 'data'라는 이름의 자식노드 생김
//
//        //dataRef.setValue()
//        dataRef.push().setValue(data);
//
//        dataRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                //값들이 여러개 이므로... foreach문
//                StringBuffer buffer= new StringBuffer();
//                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
//                    String data= snapshot.getValue(String.class);
//                    buffer.append(data);
//                }
//
//                tv.setText(buffer.toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        //4) 하나의 노드에 벨류가 여러개!
//        DatabaseReference memberRef= rootRef.child("member");
//        DatabaseReference itemRef= memberRef.push();//자식노드
//        itemRef.child("name").setValue(data);
//        itemRef.child("age").setValue(20);
//
//        //'member'라는 이름의 노드(node)에 리스너추가
//        memberRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                StringBuffer buffer= new StringBuffer();
//
//                //member안에 여러개의 자식노드들이 있으므로
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    //자식노드들 마다 'name', 'age'라는 자식노드가 또 있음.
//                    for( DataSnapshot ds : snapshot.getChildren()){
//                        buffer.append( ds.getKey()+" : "+ ds.getValue()+"\n");
//                    }
//                    buffer.append("\n");
//                }
//
//                tv.setText(buffer.toString());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        //5) 나만의 클래스(PersonVO)객체를 만들어서
        //한방에 멤버값들 저장하기
        String name= et.getText().toString();
        int age=25;
        String address="SEOUL";

        //저장할 값들을 하나의 객체로 생성
        PersonVO person= new PersonVO(name, age, address);

        //'persons'노드를 새로 생성
        DatabaseReference personRef= rootRef.child("persons");
        personRef.push().setValue(person);

        //'persons'라는 노드에 리스너 붙이기
        personRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //persons노드는 여러개의 자식노드가 있으므로
                StringBuffer buffer= new StringBuffer();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    PersonVO person= snapshot.getValue(PersonVO.class);
                    String name= person.getName();
                    int age= person.getAge();
                    String address= person.getAddress();

                    buffer.append(name+" , "+ age+" , "+ address+"\n");
                }

                tv.setText(buffer.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
