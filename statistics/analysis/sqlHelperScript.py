array = ["en_AUS_austra_int","en_AUS_hersun_int","en_CAN_starca_int","en_CHN_chinad_int","en_CHN_mopost_int","en_GBR_dailyt_int","en_IND_hindti_int","en_IND_tindia_int","en_JPN_jatime_int","en_MLT_tmalta_int","en_MYS_starmy_int","en_NGA_thiday_int","en_NZL_nzhera_int","en_PAK_newint_int","en_SGP_twoday_int","en_USA_nytime_int","en_USA_wapost_int","en_ZWE_chroni_int"]

f = open('output.sql', 'w')	
for feed in array:
	f.write("select t.name, count(*) from rss_notes n inner join tag_to_nodes ttn on n.id=ttn.nodes_id inner join tag t on t.id=ttn.tag_id where n.feed like '" + feed + "' group by 1 order by 2 desc limit 20 into outfile '" + feed + "_top20.csv' fields terminated by ',' ;\n")
	f.write("select t.name, count(*) from rss_notes n inner join tag_to_nodes ttn on n.id=ttn.nodes_id inner join tag t on t.id=ttn.tag_id where n.feed like '" + feed + "' group by 1 order by 2 desc limit 15, 20 into outfile '" + feed + "_from15.csv' fields terminated by ',' ;\n")

