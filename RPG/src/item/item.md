# Item package

## Item implements PlayerMoveObserver

所有道具的父類

(Amulet、Elixir、Sword、Tome) extend Item

## UnitsData(單例模式)

初始化工具:
 List<Record> getRecords("NPC類型") 取得npc生成資訊

 UnitsData.Record(type, posX, posY)

## DistUtils(距離計算工具函數類別)

dist()