org 0x0593; начало программы
      length: word 0x0007
      buffer: word 0x0000
      check: word 0x0001
      symbols: word 0x0597
      word 0xE5C4
      word 0xE5F0
      word 0xEEE2
      word 0x003F
      START: CLA

      len: IN 7; читаем длину слова
      AND #0x40
      BEQ len; спин-луп
      LD length
      OUT 6

      s1: IN 7; читаем первый символ
      AND #0x40
      BEQ s1; спин-луп
      LD (symbols)+
      ST buffer
      OUT 6

      s2: IN 7; читаем второй символ
      AND #0x40
      BEQ s2; спин-луп
      LD length;
      CMP check;
      BEQ loopJump;
      LD buffer
      SWAB
      OUT 6
      LD length
      DEC
      ST length
      loopJump: LOOP length
      JUMP s1
      HLT
