use caesar;
insert into role_category (`name`) values ('watcher');
insert into role_category (`name`) values ('itacademy');
insert into role_category (`name`) values ('tes');
insert into role (`name`,category_id) values ('recrouter',1);
insert into role (`name`,category_id) values ('guest',1);
insert into role (`name`,category_id) values ('teacher',2);
insert into role (`name`,category_id) values ('coordinator',2);
insert into role (`name`,category_id) values ('admin',2);
insert into role (`name`,category_id) values ('tes',3);
insert into status_category (`name`) values ('future');
insert into status_category (`name`) values ('current');
insert into status_category (`name`) values ('finished');
insert into `status` (`name`,category_id) values ('planned',1);
insert into `status` (`name`,category_id) values ('boarding',1);
insert into `status` (`name`,category_id) values ('before start',2);
insert into `status` (`name`,category_id) values ('in-process',2);
insert into `status` (`name`,category_id) values ('offering',2);
insert into `status` (`name`,category_id) values ('graduated',3);
insert into specialization (`name`) values ('oop_java_core');
insert into specialization (`name`) values ('js_core');
insert into specialization (`name`) values ('web_C#_.net');
insert into specialization (`name`) values ('testing_ISTQB');
insert into specialization (`name`) values ('HTML5_CSS3_JS_foundamentals');
insert into specialization (`name`) values ('iOS');
insert into specialization (`name`) values ('JAVA');
insert into specialization (`name`) values ('java_for_non_it');
insert into specialization (`name`) values ('.NET');
insert into specialization (`name`) values ('Database');
insert into specialization (`name`) values ('manual_quality_control');
insert into specialization (`name`) values ('ua_c++');
insert into specialization (`name`) values ('automation_testing_quality_control');
insert into specialization (`name`) values ('devops');
insert into specialization (`name`) values ('lamp');
insert into specialization (`name`) values ('web_ui');
insert into specialization (`name`) values ('web_ui_full_stack');
insert into specialization (`name`) values ('UX');
insert into specialization (`name`) values ('ua_web_ui_js');
insert into specialization (`name`) values ('quality_control_for_cloud_computing');
insert into specialization (`name`) values ('angular_boot_camp');
insert into specialization (`name`) values ('ua_go_boot_camp');
insert into specialization (`name`) values ('ua_test_automation_boot_camp_for_mobile_with_python');
insert into specialization (`name`) values ('ua_software_ingeneering_with_testing');
insert into country (`name`) values ('Ukraine');
insert into country (`name`) values ('Hungary');
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
insert into location (`name`,country_id,coordinator_id) values ('Dnipro',1,NULL);
insert into location (`name`,country_id,coordinator_id) values ('Sofia',2,NULL);
insert into user (first_name,last_name,role_id,date_of_birth,nick_name,password_hash_code,self_info,location_id) values ('Oleg','Shvets',3,'2017-01-01','OlegShvets','ghd22df','teacher in softServe. Dnipro',1);
insert into user (first_name,last_name,role_id,date_of_birth,nick_name,password_hash_code,self_info,location_id) values ('Dmytro','Petin',4,'2017-02-02','DmytroPetin','fgdfg24sd','coordinator in Dnipro',1);
insert into user (first_name,last_name,role_id,date_of_birth,nick_name,password_hash_code,self_info,location_id) values ('Lucas','Lukichich',4,'2017-03-03','LukasLukichich','fjgf24sd','coordinator in Sofia',2);
insert into `educational_group` (`name`,teacher_id,location_id,start_date,finish_date,status_id,specialization_id) values ('DP-115',1,1,'2017-04-29','2017-08-18',4,7);
insert into user (first_name,last_name,role_id,date_of_birth,nick_name,password_hash_code,self_info,location_id) values ('Lev','Bukhanets',2,'2017-04-04','Myst1c','sdfhs332w','student Lev',1);
insert into user (first_name,last_name,role_id,date_of_birth,nick_name,password_hash_code,self_info,location_id) values ('Dmytro','Kholod',2,'2017-05-05','DimaKh','sdasdfdew','student Dima',1);
insert into student_group (group_id,user_id) values (1,4);
insert into student_group (group_id,user_id) values (1,5);
insert into email (`value`,is_it_primary,user_id) values ('olshvets@gmail.com',true,1);
insert into email (`value`,is_it_primary,user_id) values ('dimapetin@gmail.com',true,2);
insert into email (`value`,is_it_primary,user_id) values ('idontafraidrussia@mail.ru',false,2);
insert into email (`value`,is_it_primary,user_id) values ('lucalukis@gmail.com',true,3);
insert into email (`value`,is_it_primary,user_id) values ('levmysticdancer@gmail.com',true,4);
insert into email (`value`,is_it_primary,user_id) values ('dimakholod@gmail.com',true,5);
insert into phone (`value`,user_id) values ('+380-96-456-22-74',1);
insert into phone (`value`,user_id) values ('+380-66-643-23-11',2);
insert into phone (`value`,user_id) values ('+344-04-252-66-32',3);
insert into contact_link (`value`,user_id) values ('https://linkedin.com/OlegShvets',1);
insert into contact_link (`value`,user_id) values ('https://linkedin.com/PapaJS',2);
insert into contact_link (`value`,user_id) values ('https://vk.com/bukhanochka',4);
update location SET coordinator_id = 2 WHERE name='Dnipro';
update location SET coordinator_id = 3 WHERE name='Sofia';
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
